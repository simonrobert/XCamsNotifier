package com.xcams.notifier;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;

import javax.swing.*;

/**
 * User: srobert
 * Date: 12/11/12
 * Time: 12:44
 */
class Notifier {

    /**
     * Handles the notifier icon in the system tray
     */
    private NotifierTrayIcon notifierTrayIcon;

    /**
     * Handles the main notifier window
     */
    private NotifierWindow notifierWindow;

    /**
     * Handles the connexion with the xcams xmpp server
     */
    private NotifierXmpp notifierXmpp;

    /**
     * Initializes the components
     */
    public Notifier() {
        // Icône dans la zone de notification (system tray)
        this.notifierTrayIcon = new NotifierTrayIcon(this);

        // Fenêtre principale
        this.notifierWindow = new NotifierWindow(this);

        // XMPP
        this.notifierXmpp = new NotifierXmpp(this);
    }

    /**
     *
     * @param url
     */
    public void goToUrl(String url) {
        this.notifierWindow.goToUrl(url);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        UIUtils.setPreferredLookAndFeel();
        NativeInterface.open();

        new Notifier();
    }
}
