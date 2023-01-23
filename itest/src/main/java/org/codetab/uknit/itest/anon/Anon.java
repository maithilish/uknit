package org.codetab.uknit.itest.anon;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Predicate;

public class Anon {

    public Thread returnAnon() {
        return new Thread() {
            @Override
            public void run() {
                super.run();
            }
        };
    }

    public void useAnon() {
        Thread t = new Thread() {
            @Override
            public void run() {
                super.run();
            }
        };
        t.start();
    }

    public Predicate<String> assignAnon() {
        Predicate<String> predicate = new Predicate<>() {
            @Override
            public boolean test(final String t) {
                return false;
            }
        };
        return predicate;
    }

    public void anonFromInterface(final StringBuilder sb, final String name) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                sb.append(name);
            }
        };
        r.run();
    }

    public Runnable returnAnonFromInterface(final StringBuilder sb,
            final String name) {
        return new Runnable() {
            @Override
            public void run() {
                sb.append(name);
            }
        };
    }

    public void useAssignedAnonInArg(final Button button) {
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                e.getSource();
            }
        };
        button.addActionListener(listener);
    }

    public void anonInArg(final StringBuilder sb) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                sb.append("run");
            }
        });
        t.start();
        sb.append("finish");
    }

    public void anonInArg(final Button button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                e.getSource();
            }
        });
    }

}
