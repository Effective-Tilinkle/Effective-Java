package com.study.effectivejava.item10;

import java.net.MalformedURLException;
import java.net.URL;

public class URLAnalysis {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("https://www.naver.com");
        URL url2 = new URL("https://www.naver.com");
        System.out.println(url.equals(url2));

//         URL class
//        public boolean equals(Object obj) {
//            if (!(obj instanceof URL))
//                return false;
//            URL u2 = (URL)obj;
//
//            return handler.equals(this, u2); // 여기 handler는 URLStreamdHandler
//        }
//
//        /////////////////////////////////////////////
//
//        // URLStreamHandler class
//        protected boolean equals(URL u1, URL u2) {
//            String ref1 = u1.getRef();
//            String ref2 = u2.getRef();
//            return (ref1 == ref2 || (ref1 != null && ref1.equals(ref2))) &&
//                    sameFile(u1, u2);
//        }
//
//        protected boolean sameFile(URL u1, URL u2) {
//            // Compare the protocols.
//            if (!((u1.getProtocol() == u2.getProtocol()) ||
//                    (u1.getProtocol() != null &&
//                            u1.getProtocol().equalsIgnoreCase(u2.getProtocol()))))
//                return false;
//
//            // Compare the files.
//            if (!(u1.getFile() == u2.getFile() ||
//                    (u1.getFile() != null && u1.getFile().equals(u2.getFile()))))
//                return false;
//
//            // Compare the ports.
//            int port1, port2;
//            port1 = (u1.getPort() != -1) ? u1.getPort() : u1.handler.getDefaultPort();
//            port2 = (u2.getPort() != -1) ? u2.getPort() : u2.handler.getDefaultPort();
//            if (port1 != port2)
//                return false;
//
//            // Compare the hosts.
//            if (!hostsEqual(u1, u2)) // 문제가 될 수 있는 부분!! => 여기서 host 주소를 불러와서 확인을 한다.. 즉, 도메인과 host ip가 달라지면 equals 에러유발! => 입력값이 같으나 출력값이 달라진다!
//                return false;
//
//            return true;
//        }
    }
}
