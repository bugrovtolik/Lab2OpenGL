package com.mycompany.lab2opengl;

import com.jogamp.opengl.util.FPSAnimator;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

public class Render implements GLEventListener {
    private double angle = 0.0;
    
    public static void main(String[] args) {       
		final GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);

		final GLCanvas glcanvas = new GLCanvas(capabilities);
		Render r = new Render();
		glcanvas.addGLEventListener(r);
		glcanvas.setSize(800, 800);

		final FPSAnimator animator = new FPSAnimator(glcanvas, 100, true);

		JFrame frame = new JFrame("OpenGL");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
			if(animator.isStarted()) {
				animator.stop();
			}
			System.exit(0);
			}
		});
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(glcanvas);

		frame.setSize(frame.getContentPane().getPreferredSize());
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		animator.start();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		for (int i = 11, alpha = 5; i >= 4; i--, alpha+=2) {
			gl.glRotated(angle, 0.0, 0.0, -0.1);
			gl.glColor4d(1.0, 0.0, 0.0, 0.01 * alpha);
			gl.glBegin(GL2.GL_POLYGON);
			for (int j = 1; j <= i; j++) {
				gl.glVertex2d(
					(i-2) * 0.1 * Math.cos(2 * Math.PI / i * j), 
					(i-2) * 0.1 * Math.sin(2 * Math.PI / i * j)
				);
			}
			gl.glEnd();
		}

		gl.glFlush();

		angle += 0.1;
		if (angle > 360) {
			angle -= 360;
		}
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
    }

    @Override
    public void init(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(1, 1, 1, 1);
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glViewport(x, y, width, height); 
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glOrtho(-1, 1, -1, 1, -1, 1);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
    }
}
