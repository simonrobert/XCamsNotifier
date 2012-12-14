package com.xcams.notifier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * User: srobert
 * Date: 12/11/12
 * Time: 12:49
 */
class NotifierTrayIcon {

    private TrayIcon trayIcon;
    private Notifier notifier;

    public NotifierTrayIcon(Notifier notifier) {
        this.notifier = notifier;

        if(!SystemTray.isSupported()) {
            return;
        }

        // Prepare the trayicon image
        Image trayImage = new ImageIcon(TrayIcon.class.getResource("/xcams.gif"), "XcamsConnect Java").getImage();
        this.trayIcon = new TrayIcon(trayImage);

        PopupMenu popupMenu = new PopupMenu();

        MenuItem menuItem = new MenuItem();
        menuItem.setLabel("Quitter");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        popupMenu.add(menuItem);

        if(Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if(desktop.isSupported(Desktop.Action.BROWSE)) {
                menuItem = new MenuItem();
                menuItem.setLabel("xcams.com");
                menuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            desktop.browse(new URI("http://xcams.com"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                });
                popupMenu.add(menuItem);
            }
        }

        this.trayIcon.setPopupMenu(popupMenu);

        final SystemTray tray = SystemTray.getSystemTray();
        try {
            tray.add(this.trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
