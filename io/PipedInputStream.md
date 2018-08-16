# PipedInputStream
a piped input stream should be connected to a piped output stream;
the piped input stream then provides whatever data bytes are written to the piped output stream.

1. the pipe is implement by `buf`.
2. can use to thread-to-thread communication.
3. single connect

Specification

1. this is ring buffer.
2. when output stream connect to input stream,the input stream cann't be null,ring buffer's position will be reset.
3. Sout-in or in-out,if try connect a connected sink,there will be a io exception.

Algorithm
1. after read() called,if (`in` == `out`),it means the buffer is empty,wait write.
2. after receive() called,if (`in` == `out`),it means the buffer is full,wait read.
```java

public synchronized int read()  throws IOException {
        if (!connected) {
            throw new IOException("Pipe not connected");
        } else if (closedByReader) {
            throw new IOException("Pipe closed");
        } else if (writeSide != null && !writeSide.isAlive()
                   && !closedByWriter && (in < 0)) {
            throw new IOException("Write end dead");
        }

        readSide = Thread.currentThread();
        int trials = 2;
        while (in < 0) {
            if (closedByWriter) {
                /* closed by writer, return EOF */
                return -1;
            }
            if ((writeSide != null) && (!writeSide.isAlive()) && (--trials < 0)) {
                throw new IOException("Pipe broken");
            }
            /* might be a writer waiting */
            notifyAll();
            try {
                wait(1000);
            } catch (InterruptedException ex) {
                throw new java.io.InterruptedIOException();
            }
        }
        int ret = buffer[out++] & 0xFF;
        if (out >= buffer.length) {
            out = 0;
        }
        if (in == out) {
            /* now empty */
            in = -1;
        }

        return ret;
}

protected synchronized void receive(int b) throws IOException {
        checkStateForReceive();
        writeSide = Thread.currentThread();
        if (in == out)
            awaitSpace();
        if (in < 0) {
            in = 0;
            out = 0;
        }
        buffer[in++] = (byte)(b & 0xFF);
        if (in >= buffer.length) {
            in = 0;
        }
}

private void awaitSpace() throws IOException {
        while (in == out) {
            checkStateForReceive();

            /* full: kick any waiting readers */
            notifyAll();
            try {
                wait(1000);
            } catch (InterruptedException ex) {
                throw new java.io.InterruptedIOException();
            }
        }
    }
```

