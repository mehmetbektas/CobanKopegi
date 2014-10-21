package org.yourorghere;

import com.sun.opengl.util.Animator;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public class KurtKuzu implements GLEventListener {

    Kare agil;
    static ArrayList<Kare> kuzu;
    static ArrayList<Fare> koordinatlar;
    ArrayList<Double> uzaklik_kurt1;
    ArrayList<Double> uzaklik_kurt2;
    Kare kurt;
    Kare kurt2;
    static Kare kopek;
    static Kare gunes;
    Fare fare;
    static int x, y;

    public KurtKuzu() {
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Sürüden ayrýlan kuzuyu kurt kapar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GLCanvas canvas = new GLCanvas();
        canvas.addGLEventListener(new KurtKuzu());
        frame.add(canvas);
        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("mouseClicked");


            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                System.out.println("mousePressed");


            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                System.out.println("mouseReleased");



            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                System.out.println("mouseEntered");


            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                System.out.println("mouseExited");


            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                super.mouseWheelMoved(e);
                System.out.println("mouseWheelMoved");


            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                System.out.println("mouseDragged");

                int x = (2800 * e.getX() / 1200);
                int y = (1600 * (680 - e.getY()) / 700);

                /**/
                if (x < kopek.x + 50 && x > kopek.x - 50 && y < kopek.y + 50 && y > kopek.y - 50) {
                    /*
                     kopek.x=x;
                     kopek.y=y;
                     */
                } /**/ else if (x < 2800 && x > 2000 && y < 200 && y > 10) {
                    Kare geciciKuzu = new Kare();
                    for (int i = 0; i < 20; i++) {
                        if (Math.abs(kuzu.get(i).x - kopek.x) < 200 && Math.abs(kuzu.get(i).y - kopek.y) < 200) {
                            if (kuzu.get(i).x < kopek.x) {
                                geciciKuzu.x = kuzu.get(i).x - Math.abs(kuzu.get(i).x - kopek.x);
                                geciciKuzu.y = kuzu.get(i).y;
                            } else {
                                geciciKuzu.x = kuzu.get(i).x + Math.abs(kuzu.get(i).x - kopek.x);
                                geciciKuzu.y = kuzu.get(i).y;
                            }
                            kuzu.set(i, geciciKuzu);
                            geciciKuzu.width = 50;

                            if (kuzu.get(i).y < kopek.y) {
                                geciciKuzu.y = kuzu.get(i).y - Math.abs(kuzu.get(i).x - kopek.x);
                                geciciKuzu.x = kuzu.get(i).x;

                            } else {
                                geciciKuzu.y = kuzu.get(i).y + Math.abs(kuzu.get(i).x - kopek.x);
                                geciciKuzu.x = kuzu.get(i).x;
                            }
                            geciciKuzu.width = 50;
                            kuzu.set(i, geciciKuzu);

                            break;

                        }
                    }
                    KurtKuzu k = new KurtKuzu();
                    k.Cal("sesler/bark.wav");
                } else if (koordinatlar.size() < 20) {
                    Fare fr = new Fare();
                    fr.x = x;
                    fr.y = y;
                    koordinatlar.add(fr);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                System.out.println("mouseMoved");
            }
        });
        frame.setSize(1200, 700);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {
                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        KurtKuzu ks = new KurtKuzu();
        ks.Cal("sesler/wolf3.wav");

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());
        // Enable VSync
        gl.setSwapInterval(1);
        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.

        x = 500;
        y = 500;

        koordinatlar = new ArrayList<Fare>();
        uzaklik_kurt1 = new ArrayList<Double>();
        uzaklik_kurt2 = new ArrayList<Double>();

        Fare fr = new Fare();
        fr.x = 2000;
        fr.y = 300;
        koordinatlar.add(fr);

        agil = new Kare();
        kopek = new Kare();
        gunes = new Kare();
        kurt = new Kare();
        kurt2 = new Kare();
        fare = new Fare();

        agil.x = 2350;
        agil.y = 900;
        agil.width = 500;
        kopek.x = 1600;
        kopek.y = 200;
        kopek.width = 50;
        gunes.x = -200;
        gunes.y = 1600;
        gunes.width = 100;

        Random rnd = new Random();

        kuzu = new ArrayList<Kare>();
        for (int i = 0; i < 20; i++) {
            Kare birkuzu = new Kare();
            birkuzu.x = rnd.nextInt(1900);
            birkuzu.y = rnd.nextInt(1400);
            birkuzu.width = 50;
            kuzu.add(birkuzu);
            /**/

            /**/
        }
        int k = rnd.nextInt(3);
        System.out.println(k);
        switch (k) {
            case 0://üst
                kurt.x = rnd.nextInt(1500);
                kurt.y = 1700;
                kurt2.x = -50;
                kurt2.y = rnd.nextInt(1500);
                break;

            case 1://sol

                kurt.x = -50;
                kurt.y = rnd.nextInt(1500);
                kurt2.x = rnd.nextInt(1500);
                kurt2.y = 1700;
                break;

            case 2://alt
                kurt.x = rnd.nextInt(2000);
                kurt.y = -50;
                kurt2.x = rnd.nextInt(1500);
                kurt2.y = 1700;
                break;


        }

        kurt.width = 50;
        kurt2.width = 50;

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!

            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslated(-14, -8, -20.0);
        /*    2800x1600 çözünürlükte ekran     */

        gl.glColor3d(0, 0.2, 0);
        Ciz_Dikdortgen(drawable, agil.x, agil.y, agil.width);

        kopek.x = koordinatlar.get(0).x;
        kopek.y = koordinatlar.get(0).y;

        if (koordinatlar.size() > 1) {
            for (int i = 0; i < koordinatlar.size(); i++) {
                gl.glColor3d(1, 1, 1);
                Ciz_Dikdortgen(drawable, koordinatlar.get(i).x, koordinatlar.get(i).y, 5);
            }
            gl.glColor3d(1, 1, 0);
            Ciz_Dikdortgen(drawable, koordinatlar.get(0).x, koordinatlar.get(0).y, kopek.width);
            koordinatlar.remove(0);
        } else {
            gl.glColor3d(1, 1, 0);
            Ciz_Dikdortgen(drawable, koordinatlar.get(0).x, koordinatlar.get(0).y, kopek.width);
        }



        Random rnd = new Random();
        int enkucuk = 99999999;
        int kuzuses = rnd.nextInt(150);

        if (kuzuses == 7) {
            KurtKuzu kusu = new KurtKuzu();
            kusu.Cal("sesler/sheep3.wav");
        }
        for (int i = 0; i < kuzu.size(); i++) {
            gl.glColor3d(0, 1, 0);
            Ciz_Dikdortgen(drawable, kuzu.get(i).x, kuzu.get(i).y, kuzu.get(i).width);
            Kare geciciKuzu = new Kare();
            geciciKuzu.x = kuzu.get(i).x + (2 * rnd.nextInt(2) - 1);
            geciciKuzu.y = kuzu.get(i).y + (2 * rnd.nextInt(2) - 1);
            geciciKuzu.width = kuzu.get(i).width;
            kuzu.set(i, geciciKuzu);


            if (Math.abs(kuzu.get(i).x - kopek.x) < 100 && Math.abs(kuzu.get(i).y - kopek.y) < 100) {
                if (kuzu.get(i).x < kopek.x) {
                    geciciKuzu.x = kuzu.get(i).x - Math.abs(kuzu.get(i).x - kopek.x);
                    geciciKuzu.y = kuzu.get(i).y;

                } else {
                    geciciKuzu.x = kuzu.get(i).x + Math.abs(kuzu.get(i).x - kopek.x);
                    geciciKuzu.y = kuzu.get(i).y;
                }
                kuzu.set(i, geciciKuzu);
                geciciKuzu.width = 50;

                if (kuzu.get(i).y < kopek.y) {
                    geciciKuzu.y = kuzu.get(i).y - Math.abs(kuzu.get(i).x - kopek.x);
                    geciciKuzu.x = kuzu.get(i).x;

                } else {
                    geciciKuzu.y = kuzu.get(i).y + Math.abs(kuzu.get(i).x - kopek.x);
                    geciciKuzu.x = kuzu.get(i).x;
                }
                geciciKuzu.width = 50;
                kuzu.set(i, geciciKuzu);
            }
            if (Math.abs(kurt.x - kuzu.get(i).x) < 5 && Math.abs(kurt.y - kuzu.get(i).y) < 5) {
                kuzu.remove(i);
            }
            if (Math.abs(kurt2.x - kuzu.get(i).x) < 5 && Math.abs(kurt2.y - kuzu.get(i).y) < 5) {
                kuzu.remove(i);
            }
            if (enkucuk > Math.abs(kuzu.get(i).x - kurt.x) && enkucuk > Math.abs(kuzu.get(i).y - kurt.y)) {
                enkucuk = i;
            }
            try {
                uzaklik_kurt1.add((Math.pow(kurt.x - kuzu.get(i).x, 2) + Math.pow(kurt.y - kuzu.get(i).y, 2)));
                uzaklik_kurt2.add((Math.pow(kurt2.x - kuzu.get(i).x, 2) + Math.pow(kurt2.y - kuzu.get(i).y, 2)));
                //   System.out.println(Math.pow(kurt.x - kuzu.get(i).x, 2) + Math.pow(kurt.y - kuzu.get(i).y, 2));
            } catch (Exception ex) {
                System.out.println(ex);
            }

            /*Math.pow(kurt.x, 2) + Math.pow(kuzu.get(i).y,2)*/

        }
        int enYakinIndisliKuzu1 = 0;
        int enYakinIndisliKuzu2 = 1;
        for (int i = 0; i < uzaklik_kurt1.size(); i++) {
            if (i == enYakinIndisliKuzu2) {
                continue;
            } else if (enkucuk > uzaklik_kurt1.get(i)) {
                enYakinIndisliKuzu1 = i;
            }

        }

        for (int i = 0; i < uzaklik_kurt2.size(); i++) {
            if (i == enYakinIndisliKuzu1) {
                continue;
            } else if (enkucuk > uzaklik_kurt2.get(i)) {
                enYakinIndisliKuzu2 = i;
            }
        }

        gl.glColor3d(1, 0, 0);
        Ciz_Dikdortgen(drawable, kurt.x, kurt.y, kurt.width);
        gl.glColor3d(1, 0, 0);
        Ciz_Dikdortgen(drawable, kurt2.x, kurt2.y, kurt.width);

        if (kurt.x < kuzu.get(enYakinIndisliKuzu1).x) {
            kurt.x++;

        } else {
            kurt.x--;
        }

        if (kurt.y < kuzu.get(enYakinIndisliKuzu1).y) {
            kurt.y++;

        } else {
            kurt.y--;
        }

        if (kurt2.x < kuzu.get(enYakinIndisliKuzu2).x) {
            kurt2.x++;

        } else {
            kurt2.x--;
        }

        if (kurt2.y < kuzu.get(enYakinIndisliKuzu2).y) {
            kurt2.y++;

        } else {
            kurt2.y--;
        }

        if (agil.x - agil.width == kurt.x && kurt.y<agil.y+2*agil.width && kurt.y>agil.y-2*agil.width) {
            kurt.x-=2;
            enYakinIndisliKuzu1++;
        }
        if (agil.x - agil.width == kurt2.x && kurt2.y<agil.y+2*agil.width && kurt2.y>agil.y-2*agil.width) {
            kurt2.y-=2;
            enYakinIndisliKuzu2++;
        }
        gl.glColor3d(0, 0, 1);
        Ciz_Dikdortgen(drawable, 2400, 65, 100);
        gl.glColor3d(0, 0, 1);
        Ciz_Dikdortgen(drawable, 2600, 65, 100);
        gl.glColor3d(1, 0.2 + (double) gunes.x / 1000, 0);
        Ciz_Dikdortgen(drawable, (double) gunes.x, gunes.y, gunes.width);
        gunes.x++;
        if (gunes.x == 3200) {
            System.exit(0);
        }

        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    private void Ciz_Dikdortgen(GLAutoDrawable drawable, double x, double y, double width) {
        GL gl = drawable.getGL();
        gl.glTranslated(x / 100, y / 100, 0.0);
        gl.glBegin(GL.GL_QUADS);
        gl.glVertex2d(-width / 100, width / 100);  // Top Left
        gl.glVertex2d(width / 100, width / 100);   // Top Right
        gl.glVertex2d(width / 100, -width / 100);  // Bottom Right
        gl.glVertex2d(-width / 100, -width / 100); // Bottom Left
        gl.glEnd();
        gl.glPopMatrix();
        gl.glTranslated(-x / 100, -y / 100, 0.0);

    }

    public void Cal(String yol) {
        try {
            // Open an audio input stream.
            URL url = this.getClass().getClassLoader().getResource(yol);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

    }
}

class Fare {

    int x, y;
}

class Kare extends Fare {

    int width;
}
