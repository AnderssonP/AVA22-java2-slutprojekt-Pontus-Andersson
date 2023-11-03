package swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import work.Buffer;
import work.Consumer;
import work.Workers;

public class JSwing{
	
	JLabel totalWorker;
	private JButton fireWorkerButton;
	private JButton addWorkerButton;
	private JPanel midPanel;
	static JProgressBar progressbar = new JProgressBar();
	static JLabel Products = new JLabel();
	Buffer buffer = new Buffer();
	int min = 3;
	int max = 7;
	
	public void jFrame() {
		JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        startConsumers();

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel header = new JLabel("Dunder Mifflin");
        header.setPreferredSize(new Dimension(300, 100));
        header.setHorizontalAlignment(JLabel.CENTER);
        header.setVerticalAlignment(JLabel.TOP);

        progressbar.setValue(0);
        progressbar.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (progressbar.getValue() <= 50) {
		            progressbar.setForeground(Color.RED);
		        } else {
		            progressbar.setForeground(Color.GREEN);
		        }
			}
        	
        });
        headerPanel.add(progressbar);
        headerPanel.add(header);
       
        midPanel = new JPanel();
        midPanel.setPreferredSize(new Dimension(500, 500));
        midPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        midPanel.add(Products);

        JPanel addPanel = new JPanel();
        addPanel.setBackground(Color.orange);
        addWorkerButton = new JButton("Add new employee");
        addWorkerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Workers.addWorkers();
                Workers employee = new Workers(buffer); 
                Thread workerThread = new Thread(employee); 
                workerThread.start(); 
                updateCountLabel();
            }
        });


        fireWorkerButton = new JButton("Fire one employee");
        fireWorkerButton.setEnabled(false);
        fireWorkerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Workers.fireWorkers();
				updateCountLabel();
			}
        	
        });
        totalWorker = new JLabel("Workers: " + Workers.getWorkers());
        addPanel.add(addWorkerButton);
        addPanel.add(fireWorkerButton);
        addPanel.add(totalWorker);

        frame.setLayout(new BorderLayout());
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(midPanel, BorderLayout.WEST);
        frame.add(addPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
        
	}
	
	 private void updateCountLabel() {
		 totalWorker.setText("Workers: "  + Workers.getWorkers());
	        if(Workers.getWorkers() == 0) {
	        	fireWorkerButton.setEnabled(false);
	        }else {
	        	fireWorkerButton.setEnabled(true);
	        }
	    } 
	 
	 private void startConsumers(){
		int consumers = (int) (Math.random() * (max - min + 1)) + min;
		
		for (int i = 1; i <= consumers; i++) {
			System.out.println(i);
			 Consumer consumer = new Consumer(buffer);
			 Thread consumerThread = new Thread(consumer);
			 consumerThread.start();
		}
	 }
	 
	 public static JProgressBar getProgressBar() {
		 return progressbar;
	 }
	 
	 public static JLabel getWorkerLabel() {
		 return Products;
	 }

}
