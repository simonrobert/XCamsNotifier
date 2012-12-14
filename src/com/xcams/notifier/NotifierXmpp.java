package com.xcams.notifier;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import java.util.Collection;

/**
 * User: srobert
 * Date: 12/11/12
 * Time: 12:52
 */
public class NotifierXmpp implements MessageListener, ChatManagerListener, RosterListener {

    private Notifier notifier;

    /**
     *
     */
    public NotifierXmpp(Notifier notifier) {
        this.notifier = notifier;

        try {
            this.connect();
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }



    /**
     * Ouvre la connection XMPP
     */
    public void connect() throws XMPPException {
        ConnectionConfiguration config = new ConnectionConfiguration("jabber.org", 5222);
        config.setCompressionEnabled(true);
        config.setSASLAuthenticationEnabled(true);

        Connection connection = new XMPPConnection(config);
        // Connect to the server
        connection.connect();
        System.out.println("XMPP connected");
        // Log into the server
        connection.login("xcamsbot@jabber.org", "xcamsbot", "XCamsConnectJava");
        System.out.println("XMPP logged in");

        Roster roster = connection.getRoster();
        roster.setSubscriptionMode(Roster.SubscriptionMode.accept_all);
        roster.addRosterListener(this);

        // Installe un répondeur simple
        connection.getChatManager().addChatListener(this);
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        System.out.println("Message reçu : "+message.getBody());
        try {
            this.notifier.goToUrl(message.getBody());
            chat.sendMessage("Browser pointé vers '" + message.getBody() + "'.");
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void chatCreated(Chat chat, boolean createdLocally) {
        if (!createdLocally) {
            chat.addMessageListener(this);
        }
    }

    @Override
    public void entriesAdded(Collection<String> strings) {
        System.out.println("XMPP entry added : " + strings.toString());
    }

    @Override
    public void entriesUpdated(Collection<String> strings) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void entriesDeleted(Collection<String> strings) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void presenceChanged(Presence presence) {
        System.out.println("XMPP presence changed : " + presence.toString());
    }
}
