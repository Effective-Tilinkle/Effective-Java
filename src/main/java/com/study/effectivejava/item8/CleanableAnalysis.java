package com.study.effectivejava.item8;

import java.io.FileInputStream;
import java.io.IOException;

public class CleanableAnalysis {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = null;
        fileInputStream.close();

        /*
        public class FileChannelImpl extends FileChannel {

            ...

            // Cleanable with an action which closes this channel's file descriptor
            private final Cleanable closer;

            private static class Closer implements Runnable {
                private final FileDescriptor fd;

                Closer(FileDescriptor fd) {
                    this.fd = fd;
                }

                public void run() {
                    try {
                        fdAccess.close(fd); // 여기서 내부적으로 native 메서드 호출을 통해 file 관련 자원에 대한 연결을 해제한다. 내부로 따라들어가다보면 "private native void close0() throws IOException;" 이거 호출
                    } catch (IOException ioe) {
                        // Rethrow as unchecked so the exception can be propagated as needed
                        throw new UncheckedIOException("close", ioe);
                    }
                }
            }

            private FileChannelImpl(FileDescriptor fd, String path, boolean readable,
                                    boolean writable, boolean direct, Object parent)
            {
                ...

                // Register a cleaning action if and only if there is no parent
                // as the parent will take care of closing the file descriptor.
                // FileChannel is used by the LambdaMetaFactory so a lambda cannot
                // be used here hence we use a nested class instead.
                this.closer = parent != null ? null :
                        CleanerFactory.cleaner().register(this, new Closer(fd)); // 혹여 클라이언트가 직접 close를 통해 자원해제를 하지않았을 경우를 대비하기위한 cleaner 처리..
            }
        }

        */
    }
}
