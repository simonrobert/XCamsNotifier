package com.xcams.notifier;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;

import javax.swing.*;
import java.awt.*;

/**
 * User: srobert
 * Date: 12/11/12
 * Time: 12:50
 */
class NotifierWindow extends WebBrowserAdapter {

    private JFrame jFrame;
    private JWebBrowser webBrowser;
    private Notifier notifier;

    public NotifierWindow(Notifier notifier) {
        this.notifier = notifier;

        final NotifierWindow nw = this;

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                nw.jFrame = new JFrame();
                nw.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // TODO à changer pour réduction en icône
                nw.jFrame.setLayout(new BorderLayout());

                nw.webBrowser = new JWebBrowser();
                nw.webBrowser.setBarsVisible(false);
                nw.webBrowser.navigate("http://xcams.com");
                System.out.println(nw.webBrowser.getBrowserVersion());

                nw.jFrame.setSize(500, 700);
                nw.jFrame.add(nw.webBrowser, BorderLayout.CENTER);

                JLabel label = new JLabel("XMPP : xcamsbot@jabber.org");

                nw.jFrame.add(label, BorderLayout.SOUTH);
                nw.jFrame.setVisible(true);
            }
        });
    }

    /**
     *
     * @param url
     */
    public void goToUrl(final String url) {
        final NotifierWindow nw = this;

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                nw.webBrowser.navigate(url);
            }
        });
    }

    @Override
    public void locationChanged(WebBrowserNavigationEvent e) {
        System.out.println(e.toString());
    }
}
