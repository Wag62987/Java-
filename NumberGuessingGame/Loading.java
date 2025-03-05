import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
public class Loading extends JFrame {
	int width=400;
	int height=600;
	String Title="Number Gusseing Game";
	JProgressBar loader;
	Loading(){
		this.setLayout(null);
		JLabel heading=new  JLabel();
		
		heading.setText(" Guess the Number ");
		heading.setForeground(new Color(224, 224, 224 ));
		heading.setFont(new Font("MV Boli",Font.BOLD,28));
		heading.setBounds(40, 150, 300, 100);
		
		JPanel panel=new JPanel();
		panel.setSize(350, 100);
		panel.setBackground(Color.white);
		panel.setBounds(15, 300, 350, 30);
		panel.setLayout(null);
		loader=new JProgressBar();
		loader.setValue(0);
		loader.setBounds(0,0,350,30);
		loader.setStringPainted(true);
		loader.setFont(new Font("MV Boli",Font.BOLD,25));
		loader.setForeground(Color.orange);
		
		//loader.setBackground(Color.white);
		panel.add(loader);
		this.add(panel);
		this.add(heading);
		this.setTitle(Title);
		this.setVisible(true);
		this.setSize(width, height);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		 this.getContentPane().setBackground(Color.black);
		 
		loading();
	}
	void loading()
	{
		int i=0;
		while(i<=100)
		{
			loader.setValue(i);
			try {
				Thread.sleep(50);
				i+=1;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		{
		if(i>=100)
			new Stage();
		this.setVisible(false);
		}
	}
}
