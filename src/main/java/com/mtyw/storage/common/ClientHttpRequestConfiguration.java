package com.mtyw.storage.common;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.util.*;

/**
 * Client configurations for accessing to MFSS services.
 */
public class ClientHttpRequestConfiguration {

    public static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT = -1;
    public static final int DEFAULT_CONNECTION_TIMEOUT = 50 * 1000;
    public static final int DEFAULT_SOCKET_TIMEOUT = 50 * 1000;
    public static final int DEFAULT_MAX_CONNECTIONS = 1024;
    public static final long DEFAULT_CONNECTION_TTL = -1;
    public static final long DEFAULT_IDLE_CONNECTION_TIME = 60 * 1000;
    public static final int DEFAULT_VALIDATE_AFTER_INACTIVITY = 2 * 1000;
    public static final int DEFAULT_THREAD_POOL_WAIT_TIME = 60 * 1000;
    public static final int DEFAULT_REQUEST_TIMEOUT = 5 * 60 * 1000;
    public static final long DEFAULT_SLOW_REQUESTS_THRESHOLD = 5 * 60 * 1000;
    public static final boolean DEFAULT_USE_REAPER = true;


    protected int connectionRequestTimeout = DEFAULT_CONNECTION_REQUEST_TIMEOUT;
    protected int connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
    protected int socketTimeout = DEFAULT_SOCKET_TIMEOUT;
    protected int maxConnections = DEFAULT_MAX_CONNECTIONS;
    protected long connectionTTL = DEFAULT_CONNECTION_TTL;
    protected long idleConnectionTime = DEFAULT_IDLE_CONNECTION_TIME;

    protected int requestTimeout = DEFAULT_REQUEST_TIMEOUT;
    protected boolean requestTimeoutEnabled = false;
    protected long slowRequestsThreshold = DEFAULT_SLOW_REQUESTS_THRESHOLD;
    protected boolean useReaper = DEFAULT_USE_REAPER;
    protected Map<String, String> defaultHeaders = new LinkedHashMap<String, String>();

    private boolean verifySSLEnable = true;
    private KeyManager[] keyManagers = null;
    private X509TrustManager[] x509TrustManagers = null;
    private SecureRandom secureRandom = null;
    private HostnameVerifier hostnameVerifier = null;

    protected boolean logConnectionPoolStats = false;



    /**
     * Gets the max connection count.
     *
     * @return The max connection count. By default it's 1024.
     */
    public int getMaxConnections() {
        return maxConnections;
    }

    /**
     * Sets the max connection count.
     *
     * @param maxConnections
     *            The max connection count.
     */
    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    /**
     * Gets the socket timeout in millisecond. 0 means infinite timeout, not
     * recommended.
     *
     * @return The socket timeout in millisecond.
     */
    public int getSocketTimeout() {
        return socketTimeout;
    }

    /**
     * Sets the socket timeout in millisecond. 0 means infinite timeout, not
     * recommended.
     *
     * @param socketTimeout
     *            The socket timeout in millisecond.
     */
    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    /**
     * Gets the socket connection timeout in millisecond.
     *
     * @return The socket connection timeout in millisecond.
     */
    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    /**
     * Sets the socket connection timeout in millisecond.
     *
     * @param connectionTimeout
     *            The socket connection timeout in millisecond.
     */
    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    /**
     * Gets the timeout in millisecond for retrieving an available connection
     * from the connection manager. 0 means infinite and -1 means not defined.
     * By default it's -1.
     *
     * @return The timeout in millisecond.
     */
    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    /**
     * Sets the timeout in millisecond for retrieving an available connection
     * from the connection manager.
     *
     * @param connectionRequestTimeout
     *            The timeout in millisecond.
     */
    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }


    /**
     * Gets the connection TTL (time to live). Http connection is cached by the
     * connection manager with a TTL.
     *
     * @return The connection TTL in millisecond.
     */
    public long getConnectionTTL() {
        return connectionTTL;
    }

    /**
     * Sets the connection TTL (time to live). Http connection is cached by the
     * connection manager with a TTL.
     *
     * @param connectionTTL
     *            The connection TTL in millisecond.
     */
    public void setConnectionTTL(long connectionTTL) {
        this.connectionTTL = connectionTTL;
    }
    /**
     * Gets the flag of using {@link IdleConnectionReaper} to manage expired
     * connection.
     */
    public boolean isUseReaper() {
        return useReaper;
    }

    /**
     * Sets the flag of using {@link IdleConnectionReaper} to manage expired
     * connection.
     */
    public void setUseReaper(boolean useReaper) {
        this.useReaper = useReaper;
    }

    /**
     * Gets the connection's max idle time. If a connection has been idle for
     * more than this number, it would be closed.
     *
     * @return The connection's max idle time in millisecond.
     */
    public long getIdleConnectionTime() {
        return idleConnectionTime;
    }

    /**
     * Sets the connection's max idle time. If a connection has been idle for
     * more than this number, it would be closed.
     *
     * @param idleConnectionTime
     *            The connection's max idle time in millisecond.
     */
    public void setIdleConnectionTime(long idleConnectionTime) {
        this.idleConnectionTime = idleConnectionTime;
    }

    /**
     * The connection idle time threshold in millisecond that triggers the
     * validation. By default it's 2000.
     *
     * @return The connection idle time threshold.
     */
    public int getValidateAfterInactivity() {
        return DEFAULT_VALIDATE_AFTER_INACTIVITY;
    }

    /**
     * Gets the flag of enabling request timeout. By default it's disabled.
     *
     * @return true enabled; false disabled.
     */
    public boolean isRequestTimeoutEnabled() {
        return requestTimeoutEnabled;
    }

    /**
     * Gets the flag of enabling request timeout. By default it's disabled.
     *
     * @param requestTimeoutEnabled
     *            true to enable; false to disable.
     */
    public void setRequestTimeoutEnabled(boolean requestTimeoutEnabled) {
        this.requestTimeoutEnabled = requestTimeoutEnabled;
    }

    /**
     * Sets the timeout value in millisecond. By default it's 5 min.
     */
    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    /**
     * Gets the timeout value in millisecond.
     */
    public int getRequestTimeout() {
        return requestTimeout;
    }

    /**
     * Sets the slow request's latency threshold. If a request's latency is more
     * than it, the request will be logged. By default the threshold is 5 min.
     */
    public long getSlowRequestsThreshold() {
        return slowRequestsThreshold;
    }

    /**
     * Gets the slow request's latency threshold. If a request's latency is more
     * than it, the request will be logged.
     */
    public void setSlowRequestsThreshold(long slowRequestsThreshold) {
        this.slowRequestsThreshold = slowRequestsThreshold;
    }

    /**
     * Gets the default http headers. All these headers would be automatically
     * added in every request. And if a header is also specified in the request,
     * the default one will be overwritten.
     */
    public Map<String, String> getDefaultHeaders() {
        return defaultHeaders;
    }

    /**
     * Sets the default http headers. All these headers would be automatically
     * added in every request. And if a header is also specified in the request,
     * the default one will be overwritten.
     *
     * @param defaultHeaders
     *            Default http headers.
     */
    public void setDefaultHeaders(Map<String, String> defaultHeaders) {
        this.defaultHeaders = defaultHeaders;
    }

    /**
     * Add a default header into the default header list.
     *
     * @param key
     *            The default header name.
     * @param value
     *            The default header value.
     */
    public void addDefaultHeader(String key, String value) {
        this.defaultHeaders.put(key, value);
    }

    /**
     * Gets the flag of verifing SSL certificate. By default it's true.
     *
     * @return true verify SSL certificate;false ignore SSL certificate.
     */
    public boolean isVerifySSLEnable() {
        return verifySSLEnable;
    }

    /**
     * Sets the flag of verifing SSL certificate.
     *
     * @param verifySSLEnable
     *            True to verify SSL certificate; False to ignore SSL certificate.
     */
    public void setVerifySSLEnable(boolean verifySSLEnable) {
        this.verifySSLEnable = verifySSLEnable;
    }

    /**
     * Gets the KeyManagers are responsible for managing the key material
     * which is used to authenticate the local SSLSocket to its peer.
     *
     * @return the key managers.
     */
    public KeyManager[] getKeyManagers() {
        return keyManagers;
    }

    /**
     * Sets the key managers are responsible for managing the key material
     * which is used to authenticate the local SSLSocket to its peer.
     *
     * @param keyManagers
     *            the key managers
     */
    public void setKeyManagers(KeyManager[] keyManagers) {
        this.keyManagers = keyManagers;
    }

    /**
     * Gets the instance of this interface manage which X509 certificates
     * may be used to authenticate the remote side of a secure socket.
     *
     * @return the x509 trust managers .
     */
    public X509TrustManager[] getX509TrustManagers() {
        return x509TrustManagers;
    }

    /**
     * Sets the instance of this interface manage which X509 certificates
     * may be used to authenticate the remote side of a secure socket.
     *
     * @param x509TrustManagers
     *            x509 trust managers
     */
    public void setX509TrustManagers(X509TrustManager[] x509TrustManagers) {
        this.x509TrustManagers = x509TrustManagers;
    }

    /**
     * Gets the cryptographically strong random number.
     *
     * @return random number.
     */
    public SecureRandom getSecureRandom() {
        return secureRandom;
    }

    /**
     * Sets the cryptographically strong random number.
     *
     * @param secureRandom
     *            the cryptographically strong random number
     */
    public void setSecureRandom(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
    }

    /**
     * Gets the instance of this interface for hostname verification.
     *
     * @return the hostname verification instance.
     */
    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }

	/**
     * Sets instance of this interface for hostname verification.
     *
     * @param hostnameVerifier
     *            the hostname verification instance
     */
    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
    }

    /**
     * Sets the flag of logging connection pool statistics.
     *
     * @param enabled
     *            True if it's enabled; False if it's disabled.
     */
    public void setLogConnectionPoolStats(boolean enabled) {
        this.logConnectionPoolStats = enabled;
    }

    /**
     * Gets the flag of logging connection pool statistics. By default it's disabled.
     *
     * @return true enabled; false disabled.
     */
    public boolean isLogConnectionPoolStatsEnable() {
        return logConnectionPoolStats;
    }

}
