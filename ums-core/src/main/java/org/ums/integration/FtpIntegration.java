package org.ums.integration;

/**
 * Created by Monjur-E-Morshed on 16-Jul-17.
 */
/*
 * @IntegrationComponentScan public class FtpIntegration {
 * 
 * @Bean public SessionFactory<FTPFile> ftpSessionFactory() { DefaultFtpSessionFactory sf = new
 * DefaultFtpSessionFactory(); sf.setHost("localhost"); sf.setPort(21); sf.setUsername("iums");
 * sf.setPassword("austig100"); return new CachingSessionFactory<FTPFile>(sf); }
 * 
 * @Bean
 * 
 * @ServiceActivator(inputChannel = "ftpChannel") public MessageHandler handler() {
 * FtpMessageHandler handler = new FtpMessageHandler(ftpSessionFactory());
 * handler.setRemoteDirectoryExpression(new LiteralExpression("remote-target-dir"));
 * handler.setFileNameGenerator(new FileNameGenerator() {
 * 
 * @Override public String generateFileName(Message<?> pMessage) { return
 * pMessage.getPayload().toString(); } }); return handler; }
 * 
 * @Bean
 * 
 * @ServiceActivator(inputChannel = "ftpChannel") public MessageHandler fileFetchingMessageHandler()
 * { FtpOutboundGateway ftpOutboundGateway = new FtpOutboundGateway(ftpSessionFactory(), "get");
 * ftpOutboundGateway.setOutputChannelName("replyChannel"); return ftpOutboundGateway; }
 * 
 * }
 */
