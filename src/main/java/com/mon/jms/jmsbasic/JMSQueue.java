package com.mon.jms.jmsbasic;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
/**
 * Basic JMS Queue
 */
public class JMSQueue {
    public static void main(String[] args) {
    	InitialContext initialContext = null;
		Connection connection = null;

		try {
			initialContext = new InitialContext();
			ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
			 connection = cf.createConnection();
			Session session = connection.createSession();
			Queue queue = (Queue) initialContext.lookup("queue/myFirstQueue");
			MessageProducer producer = session.createProducer(queue);
			TextMessage message = session.createTextMessage("Hello, How are you?");
			producer.send(message);
			System.out.println("Message Sent: " + message.getText());

			MessageConsumer consumer = session.createConsumer(queue);
			connection.start();
			TextMessage messageReceived = (TextMessage) consumer.receive(5000);
			System.out.println("Message Received: " + messageReceived.getText());

		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}finally {
			if(initialContext!=null) {
				try {
					initialContext.close();
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
			
			if(connection!=null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
    }
}
