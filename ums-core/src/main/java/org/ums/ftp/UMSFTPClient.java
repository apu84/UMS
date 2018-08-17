package org.ums.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


public class UMSFTPClient extends FTPClient {
    private int __dataTimeout;
    private int __activeMinPort = 3000;
    private int __activeMaxPort = 4000;
    private Random __random = new Random();

    public UMSFTPClient(final int dataTimeout,
                        final int activeMaxPort,
                        final int activeMinPort) {
        __dataTimeout = dataTimeout;
        __activeMaxPort = activeMaxPort;
        __activeMinPort = activeMinPort;
    }

    @Override
    protected Socket _openDataConnection_(String command, String arg) throws IOException {
        if (getDataConnectionMode() != ACTIVE_LOCAL_DATA_CONNECTION_MODE &&
                getDataConnectionMode() != PASSIVE_LOCAL_DATA_CONNECTION_MODE) {
            return null;
        }

        final boolean isInet6Address = getRemoteAddress() instanceof Inet6Address;

        Socket socket;

        if (getDataConnectionMode() == ACTIVE_LOCAL_DATA_CONNECTION_MODE) {
            // if no activePortRange was set (correctly) -> getActivePort() = 0
            // -> new ServerSocket(0) -> bind to any free local port
            ServerSocket server = _serverSocketFactory_.createServerSocket(getActivePort(), 1, getLocalAddress());

            try {
                // Try EPRT only if remote server is over IPv6, if not use PORT,
                // because EPRT has no advantage over PORT on IPv4.
                // It could even have the disadvantage,
                // that EPRT will make the data connection fail, because
                // today's intelligent NAT Firewalls are able to
                // substitute IP addresses in the PORT command,
                // but might not be able to recognize the EPRT command.
                if (isInet6Address) {
                    if (!FTPReply.isPositiveCompletion(eprt(getLocalAddress(), server.getLocalPort()))) {
                        return null;
                    }
                } else {
                    if (!FTPReply.isPositiveCompletion(port(getLocalAddress(), server.getLocalPort()))) {
                        return null;
                    }
                }

                if ((getRestartOffset() > 0) && !restart(getRestartOffset())) {
                    return null;
                }

                if (!FTPReply.isPositivePreliminary(sendCommand(command, arg))) {
                    return null;
                }

                // For now, let's just use the data timeout value for waiting for
                // the data connection.  It may be desirable to let this be a
                // separately configurable value.  In any case, we really want
                // to allow preventing the accept from blocking indefinitely.
                if (__dataTimeout >= 0) {
                    server.setSoTimeout(__dataTimeout);
                }
                socket = server.accept();

                // Ensure the timeout is set before any commands are issued on the new socket
                if (__dataTimeout >= 0) {
                    socket.setSoTimeout(__dataTimeout);
                }
                if (getReceiveDataSocketBufferSize() > 0) {
                    socket.setReceiveBufferSize(getReceiveDataSocketBufferSize());
                }
                if (getSendDataSocketBufferSize() > 0) {
                    socket.setSendBufferSize(getSendDataSocketBufferSize());
                }
            } finally {
                server.close();
            }

            return socket;
        }

        return super._openDataConnection_(command, arg);
    }

    private int getActivePort() {
        if (__activeMinPort > 0 && __activeMaxPort >= __activeMinPort) {
            if (__activeMaxPort == __activeMinPort) {
                return __activeMaxPort;
            }
            // Get a random port between the min and max port range
            return __random.nextInt(__activeMaxPort - __activeMinPort + 1) + __activeMinPort;
        } else {
            // default port
            return 0;
        }
    }
}
