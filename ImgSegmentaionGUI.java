
package SAYA;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class ImgSegmentaionGUI {
    JFrame imgSegmentMainFrame, imgSegmentInitalFrame, perEvaluationFrame ;
    JLabel frontInfoL10,frontInfoL1,frontInfoL2,frontInfoL3,frontInfoL4,frontInfoL5,frontInfoL6, frontInfoL7, emptyL1,emptyL2,emptyL3;
	JPanel  frontInfoP10,frontInfoP1,frontInfoP2,frontInfoP3,frontInfoP4,frontInfoP5,frontInfoP6, frontInfoP7, emptyP1,emptyP2,emptyP3;
	
	Color bgColor= new Color(140,241,211);//255, 252,183); //208,222,180);////177,191,209);//199,217,241);////238,221,236 //197,193,137
	Color fgColor= new Color(0,32,96);//15,36,62);//15,27,23);//99,36,35);//192,0,0);////////////////
    
	String filename;
	String select = "class";
	Vector clsNamesV = new Vector(); 
	
	JFileChooser fch ;
	File file=null;
	boolean temp;
	JPanel imgP;
	JButton contB, browseB;
    JPanel contP, searchP, nameP, logoP, showP;
    JLabel nameL,logoL, keywordL, showL;
    JTextField keywordTF;
    
    public void ImgSegmentInitGUI()
	{
		imgSegmentInitalFrame = new JFrame("A SALIENT REGION EXTRACTION BASED"+ " ON COLOUR AND TEXTURE FEATURES");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		//Image img = Toolkit.getDefaultToolkit().getImage("src/logol.jpg");
		//imgSegmentInitalFrame.setIconImage(img);
		
		imgSegmentInitalFrame.setBounds(0, 0, (int) dim.getWidth(), (int) dim.getHeight()-10);
		imgSegmentInitalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		imgSegmentInitalFrame.setVisible(true);
		imgSegmentInitalFrame.setLayout(new GridLayout(12,1));
		imgSegmentInitalFrame.setResizable(true);
		//autoPRGInitalFrame.setPreferredSize(getMaximumSize());
		//imgSegmentInitalFrame.setSize(JFrame.MAXIMIZED_HORIZ,JFrame.MAXIMIZED_VERT);
		//setForeground(fgColor);
		Container con = imgSegmentInitalFrame.getContentPane(); 
		con.setBackground(bgColor);//238,221,236));//199,217,241));
		
		frontInfoL1 = new JLabel();
		 frontInfoL2 = new JLabel();
		 frontInfoL3 = new JLabel();
		 frontInfoL4 = new JLabel();
		 frontInfoL5 = new JLabel();
		 frontInfoL6 = new JLabel();
		 frontInfoL7 = new JLabel();
                 frontInfoL10 = new JLabel();
		 emptyL1 = new JLabel();
		 emptyL2 = new JLabel();
		 emptyL3 = new JLabel();
		 
		 frontInfoP1 = new JPanel();
		 frontInfoP2 = new JPanel();
		 frontInfoP3 = new JPanel();
		 frontInfoP4 = new JPanel();
		 frontInfoP5 = new JPanel();
		 frontInfoP6 = new JPanel();
		 frontInfoP7 = new JPanel();
                 frontInfoP10 = new JPanel();
		
		 emptyP1 = new JPanel();
		 emptyL1.setText("");
		 emptyP1.setBackground(bgColor);//199,217,241));//131,156,153));
		 emptyP1.add(emptyL1);
		 con.add(emptyP1);
		 
		 
		 frontInfoL1.setText("    Welcome   ");
		 frontInfoL1.setFont(new Font("Times new Roman",Font.ITALIC,35));
		 frontInfoP1.add(frontInfoL1);
		 frontInfoP1.setBackground(bgColor);//199,217,241));//131,156,153));
		 frontInfoL1.setForeground(fgColor);
		 con.add(frontInfoP1);
		 
		 frontInfoL2.setText("A SALIENT REGION EXTRACTION BASED");
		 frontInfoL2.setFont(new Font("Times new Roman",Font.ITALIC,44));
		 frontInfoP2.add(frontInfoL2);
		 frontInfoP2.setBackground(bgColor);//199,217,241));//131,156,153));
		 frontInfoL2.setForeground(fgColor);//new Color(15,41,74));
		 con.add(frontInfoP2);
                 
                 frontInfoL10.setText("ON COLOUR AND TEXTURE FEATURES");
		 frontInfoL10.setFont(new Font("Times new Roman",Font.ITALIC,44));
		 frontInfoP10.add(frontInfoL10);
		 frontInfoP10.setBackground(bgColor);//199,217,241));//131,156,153));
		 frontInfoL10.setForeground(fgColor);//new Color(15,41,74));
		 con.add(frontInfoP10);
		 
		 emptyP2 = new JPanel();
		 emptyL2.setText("");
		 emptyP2.setBackground(bgColor);//199,217,241));//131,156,153));
		 emptyP2.add(emptyL2);
		 con.add(emptyP2);
		 
		 frontInfoL3.setText("Project Prepared By :");
		 frontInfoL3.setFont(new Font("Times new Roman",Font.ITALIC,32));
		 frontInfoP3.add(frontInfoL3);
		 frontInfoP3.setBackground(bgColor);//199,217,241));//131,156,153));
		 frontInfoL3.setForeground(fgColor);//new Color(15,41,74));
		 con.add(frontInfoP3);
		 
		 frontInfoL4.setText(" Aakarsh		     ");
		 frontInfoL4.setFont(new Font("Times new Roman",Font.ITALIC,32));
		 frontInfoP4.add(frontInfoL4);
		 frontInfoP4.setBackground(bgColor);//199,217,241));//131,156,153));
		 frontInfoL4.setForeground(fgColor);//new Color(15,41,74));
		 con.add(frontInfoP4);
		 
		 frontInfoL5.setText(" Shitiz Aggarwal	    ");
		 frontInfoL5.setFont(new Font("Times new Roman",Font.ITALIC,32));
		 frontInfoP5.add(frontInfoL5);
		 frontInfoP5.setBackground(bgColor);//199,217,241));//131,156,153));
		 frontInfoL5.setForeground(fgColor);//new Color(15,41,74));
		 con.add(frontInfoP5);
		 
		 frontInfoL6.setText("        ( Computer Science  4Th Year )");
		 frontInfoL6.setFont(new Font("Times new Roman",Font.ITALIC,28));
		 frontInfoP6.add(frontInfoL6);
		 frontInfoP6.setBackground(bgColor);//199,217,241));//131,156,153));
		 frontInfoL6.setForeground(fgColor);//new Color(15,41,74));
		 con.add(frontInfoP6);
		 
		 frontInfoL7.setText("      Project Mentor :  Arti Gautam  ");
		 frontInfoL7.setFont(new Font("Times new Roman",Font.ITALIC,28));
		 frontInfoP7.add(frontInfoL6);
		 frontInfoP7.setBackground(bgColor);//199,217,241));//131,156,153));
		 frontInfoL7.setForeground(fgColor);//new Color(15,41,74));
		 con.add(frontInfoP7);
		 
		 
		 emptyP2 = new JPanel();
		 emptyL2.setText("");
		 emptyP2.setBackground(bgColor);//199,217,241));//131,156,153));
		 emptyP2.add(emptyL2);
		 con.add(emptyP2);
		 
		 
		 contB = new JButton(" Continue.... "); 
		 contP = new JPanel();
		 contP.setLayout(new FlowLayout());
		 contP.setBackground(bgColor);//131,156,153));
		 //contB.setBackground(new Color(217,149,148));//fgColor);
		 contB.setForeground(fgColor);
		 contP.add(contB);
		 con.add(contP);
		 contB.setVisible(true);
		 
		contB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ae) {
					              
				imgSegmentInitalFrame.setVisible(false);
				imgSegmentMainGUI();
			}
		});
	} 	 

    public void imgSegmentMainGUI()
	{
		imgSegmentMainFrame = new JFrame("A SALIENT REGION EXTRACTION BASED"+ " ON COLOUR AND TEXTURE FEATURES");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		//Image img = Toolkit.getDefaultToolkit().getImage("src/logol.jpg");
		//imgSegmentMainFrame.setIconImage(img);
		//autoPRGInitalFrame.setName("Automatic Image Data Mining And Segmentaion");
		imgSegmentMainFrame.setBounds(0, 0, (int) dim.getWidth(),(int) dim.getHeight()-30);
		imgSegmentMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		imgSegmentMainFrame.setVisible(true);
		imgSegmentMainFrame.setLayout(new GridLayout(12,1));
		imgSegmentMainFrame.setResizable(true);
		//setForeground(fgColor);
		final Container con = imgSegmentMainFrame.getContentPane(); 
		con.setBackground(bgColor);//238,221,236));//199,217,241));
		
		//Image logo = Toolkit.getDefaultToolkit().getImage("src/Logo.jpg");
		
		/* emptyP1 = new JPanel();
		 emptyL1.setText("");
		 emptyP1.setBackground(bgColor);//199,217,241));//131,156,153));
		 emptyP1.add(emptyL1);
		 con.add(emptyP1);
		*/
		logoL = new JLabel();
		logoP = new JPanel(); 
		
		
		
		//ImageIcon ii = new ImageIcon(img);
		//logoL.setIcon(ii);
		//logoL.setSize(1, 1);
		
		//con.add(logoP);
		
		nameL = new JLabel("A SALIENT REGION EXTRACTION BASED"+ " ON COLOUR AND TEXTURE FEATURES");
		nameP = new JPanel();
		nameL.setFont(new Font("Times new Roman",Font.ITALIC,30));
		nameP.add(nameL);
		nameP.setBackground(bgColor);//199,217,241));//131,156,153));
		nameL.setForeground(fgColor);//new Color(15,41,74));
		con.add(nameP);

		
		
		//final JRadioButton learningRB = new JRadioButton("Learning");  LC
		final JRadioButton classfcnRB = new JRadioButton("Classification");
		final ButtonGroup RBG = new ButtonGroup();
		JPanel radioP = new JPanel();
		radioP.setForeground(fgColor);
		radioP.setBackground(bgColor);
		 
		/*radioP.add(learningRB);
		learningRB.setBackground(bgColor);
		learningRB.setForeground(fgColor);
		learningRB.setFont(new Font("Times new Roman",Font.PLAIN,18));
		
		
		learningRB.addActionListener();
		radioP.add(classfcnRB);
		classfcnRB.setBackground(bgColor);
		classfcnRB.setForeground(fgColor);
		classfcnRB.setFont(new Font("Times new Roman",Font.PLAIN,18));   LC*/
		
		
		JButton EvaluateB = new JButton("Performance Evaluation");
		EvaluateB.setFont(new Font("Times new Roman",Font.PLAIN,18));
		EvaluateB.setBackground(bgColor);
		EvaluateB.setForeground(fgColor);
		EvaluateB.setBorderPainted(false);
		EvaluateB.setFocusable(false);
		radioP.add(EvaluateB);
		EvaluateB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			     
				perEvaluationGUI();
				
			}
		});
		
		//RBG.add(learningRB);
		RBG.add(classfcnRB);
		if(select.equals("class"))
		classfcnRB.setSelected(true);
		else
			//learningRB.setSelected(true); LC
		//RBG.setSelected(arg0, arg1)
		con.add(radioP);
		
		keywordL = new JLabel("Select Image File : ");
		keywordTF = new JTextField(40);
		browseB = new JButton(" Browse ");
		searchP = new JPanel();
		
		keywordL.setForeground(fgColor);
		keywordL.setFont(new Font("Times new Roman",Font.PLAIN,18));
		searchP.setBackground(bgColor);
		searchP.add(keywordL);
		//searchP.add(keywordTF);
		searchP.add(browseB);
		con.add(searchP);
		//temp=true;
		fch=new JFileChooser();
		browseB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				//imgSegmentMainFrame.dispose();
				int retval=fch.showOpenDialog(browseB);
		  		if(retval==JFileChooser.APPROVE_OPTION)
		  		{
		  			
		  			
		  			//if(learningRB.isSelected())  LC
		  			//	select = "learn";   LC
		  			
		  			if(classfcnRB.isSelected())
		  				select = "class";
		  			
		  			//System.out.println(" here i am at 278 "+ learningRB.isSelected());
		  			
		  			file=fch.getSelectedFile();
		  			filename=file.getName();
		  			//System.out.println("filename="+filename);
		  			imgSegmentMainFrame.setVisible(false);
		  			showImage(file);
		  			
		  			
		  			//temp=false;
		  		}
				//imgSegmentGetSearchResult(keywordTF.getText());
			 	
			}
		});
		
	}
    
    
    public void showImage(File filen)
	 {
		 imgSegmentMainFrame = new JFrame("A SALIENT REGION EXTRACTION BASED"+ " ON COLOUR AND TEXTURE FEATURES");
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			//Image img = Toolkit.getDefaultToolkit().getImage("src/logol.jpg");
			//imgSegmentMainFrame.setIconImage(img);
			//autoPRGInitalFrame.setName("Automatic Image Data Mining And Segmentaion");
			imgSegmentMainFrame.setBounds(0, 0, (int) dim.getWidth(),(int) dim.getHeight()-30);
			imgSegmentMainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			imgSegmentMainFrame.setVisible(true);
			//imgSegmentMainFrame.setLayout(new FlowLayout());
			imgSegmentMainFrame.setResizable(true);
			//setForeground(fgColor);
			final Container con = imgSegmentMainFrame.getContentPane(); 
			con.setBackground(bgColor);//238,221,236));//199,217,241));
			
					
			nameL = new JLabel("A SALIENT REGION EXTRACTION BASED"+ " ON COLOUR AND TEXTURE FEATURES");
			nameP = new JPanel();
			nameL.setFont(new Font("Times new Roman",Font.ITALIC,30));
			nameP.add(nameL);
			nameP.setBackground(bgColor);//199,217,241));//131,156,153));
			nameL.setForeground(fgColor);//new Color(15,41,74));
			
			JLabel imgL =new JLabel();
			final JLabel waitL =new JLabel("Processing Please wait.......");
			waitL.setVisible(false);
			
			JPanel waitP = new JPanel();
			waitP.add(waitL);
			
			BufferedImage bimg = null;
			  try {
			      bimg = ImageIO.read(new File(filen.getAbsolutePath()));
			  } catch (IOException e) {
			  }
			
			 imgP=new JPanel();
			 imgP.setBackground(bgColor);
			 //l.setBounds(0, 0, 5,4);//bimg.getWidth(), bimg.getHeight());
			//Image img = Toolkit.getDefaultToolkit().getImage(filen.getAbsolutePath());
			
			 Image img = Toolkit.getDefaultToolkit().getImage(filen.getAbsolutePath());
		     ImageIcon icon = new ImageIcon(img);
             imgL.setIcon(icon);
             //imgP.add(l);
              imgP.add(imgL);
             
             JButton stSegB = new JButton(" Show Segments ");
             JButton backB = new JButton(" Go Back ");
             //changes made
             JLabel newvariable =new JLabel();
			final JLabel newvar =new JLabel("The image has been processed. "
                                + "You can see the images at the following destined folder : "
                                + "C:\\Users\\Shitiz\\Documents\\NetBeansProjects\\XYZ");
                        JPanel newvarP = new JPanel();
			newvarP.add(newvar);
     		JPanel stSegP = new JPanel();
     		
     				
             
             
             stSegP.add(newvar);
             stSegP.add(stSegB);
             stSegP.add(backB);
             stSegP.add(waitL);
             stSegP.setBackground(bgColor);
             JPanel mainjp =new JPanel();
             
             JScrollPane jsp = new JScrollPane(imgP);
             jsp.setBackground(bgColor);
             final String fpath=filen.getAbsolutePath();
             stSegB.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					waitL.setVisible(true);
					 //wait(5);
					//imgSegmentMainFrame.();
					ImageSegmentation imgSegmentCall = new  ImageSegmentation();
		  			int numSeg=imgSegmentCall.imgSegmentationMain(file);
					imgSegmentMainFrame.dispose();
		  			
		  			showSegment(numSeg);
					
					
				}
			});
             backB.addActionListener(new ActionListener() {
 				
 				@Override
 				public void actionPerformed(ActionEvent arg0) {
 					// TODO Auto-generated method stub
 					 //wait(5);
 					
 					imgSegmentMainFrame.dispose();
 					imgSegmentMainGUI();
 					
 					
 				}
 			});
             
             con.add(nameP,BorderLayout.NORTH);
             con.add(jsp, BorderLayout.CENTER);
             con.add(stSegP,BorderLayout.SOUTH);
             
	 }
	
    public void showSegment(int numSeg)
	{
		imgSegmentMainFrame = new JFrame("A SALIENT REGION EXTRACTION BASED"+ " ON COLOUR AND TEXTURE FEATURES");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		//Image img = Toolkit.getDefaultToolkit().getImage("src/logol.jpg");
		//imgSegmentMainFrame.setIconImage(img);
		//autoPRGInitalFrame.setName("Automatic Image Data Mining And Segmentaion");
		imgSegmentMainFrame.setBounds(0, 0, (int) dim.getWidth(),(int) dim.getHeight()-30);
		imgSegmentMainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		imgSegmentMainFrame.setVisible(true);
		//imgSegmentMainFrame.setLayout(new FlowLayout());
		imgSegmentMainFrame.setResizable(true);
		//setForeground(fgColor);
		final Container con = imgSegmentMainFrame.getContentPane(); 
		con.setBackground(bgColor);//238,221,236));//199,217,241));
		
		nameL = new JLabel("A SALIENT REGION EXTRACTION BASED"+ " ON COLOUR AND TEXTURE FEATURES");
		nameP = new JPanel();
		nameL.setFont(new Font("Times new Roman",Font.ITALIC,30));
		nameP.add(nameL);
		nameP.setBackground(bgColor);//199,217,241));//131,156,153));
		nameL.setForeground(fgColor);//new Color(15,41,74));
		con.add(nameP,BorderLayout.NORTH);
                
                showL = new JLabel("The image has been processed. "
                                + "You can see the images at the following destined folder : "
                                + "C:\\Users\\Shitiz\\Documents\\NetBeansProjects\\XYZ");
		showP = new JPanel();
		showL.setFont(new Font("Times new Roman",Font.BOLD,20));
		showP.add(showL);
		showP.setBackground(bgColor);//199,217,241));//131,156,153));
		showL.setForeground(fgColor);//new Color(15,41,74));
		con.add(showP,BorderLayout.CENTER);
                
		String fileName = new String();
		String []ff=file.getName().split("\\.");
		fileName=ff[0];
		
		JPanel mainP=new JPanel();
                
		
		mainP.setLayout(new GridLayout(numSeg+3,1));
		mainP.setBackground(bgColor);
        mainP.setForeground(fgColor);
        
		mainP.add(imgP);
		
		/*BufferedImage bimg = null;
		  try {
		      bimg = ImageIO.read(new File(fileName+"Edging.jpg"));
		  } catch (IOException e) {
		  }
		*/
		 JPanel imP=new JPanel();
		//l.setBounds(0, 0, 5,4);//bimg.getWidth(), bimg.getHeight());
		//Image img = Toolkit.getDefaultToolkit().getImage(filen.getAbsolutePath());
		 JLabel imgL  = new JLabel(); 
		 Image img = Toolkit.getDefaultToolkit().getImage(fileName+"Edging.jpg");
	     ImageIcon icon = new ImageIcon(img);
         imgL.setIcon(icon);
         imgL.setBackground(bgColor);
         imgL.setForeground(fgColor);
         //imgP.add(l);
         //System.out.println(numSeg);
         
         clsNamesV.clear();
         
         JLabel opClassNameL[] = new JLabel[numSeg+1]; //  output class name 
         
         JLabel[] listL =new JLabel[numSeg+1];
         final JLabel[] segNameL =new JLabel[numSeg+1];
         final JTextField[] segNameTF = new JTextField[numSeg+1];
         final JButton[] okB = new JButton[numSeg+1]; 
         JButton[] inCorrectB = new JButton[numSeg+1];
         
         
         final JComboBox[] jc = new JComboBox[numSeg+1];
        
         //System.out.println("here i am at 1");
         
         JPanel[] segP = new JPanel[numSeg+1];
         JPanel comP;
         
         mainP.add(imgL);
         //mainP.setLayout(new GridLayout());
         ImageSegmentation imgseg = new ImageSegmentation();
         
         final String[] segInfo = new String[numSeg];
         for(int i=1;i<=numSeg;i++)
		{
        	 img = Toolkit.getDefaultToolkit().getImage(fileName+"Clr"+i+".jpg");
    	     icon = new ImageIcon(img);
    	     segP[i] = new JPanel(new GridLayout(2,1));//
    	     
    	     segP[i].setBackground(bgColor);
    	     segP[i].setForeground(bgColor);
    	     comP = new JPanel(new FlowLayout());
    	     
    	     comP.setBackground(bgColor);
    	     comP.setForeground(bgColor);
    	     
    	     listL[i]=new JLabel();
    	     segNameL[i] = new JLabel();
    	     segNameTF[i] = new JTextField(10);
    	     segNameTF[i].setForeground(fgColor);
    	     segNameTF[i].setFont(new Font("Times new Roman",Font.BOLD,14));
    	     jc[i] = new JComboBox();
    	     jc[i].setForeground(fgColor);
    	     jc[i].setBackground(bgColor);
    	     //jc[i].setFont(new Font("Times new Roman",Font.PLAIN,12));
    	     okB[i] = new JButton("Ok");
    	     okB[i].setBackground(bgColor);
    	     okB[i].setForeground(fgColor);
    	     segNameL[i].setForeground(fgColor);
    	     segNameL[i].setBackground(bgColor);
    	     listL[i].setSize(100,100);
    	     
    	     listL[i].setIcon(icon);
    	     segP[i].add(listL[i]);
    	     
    	     
    	     //mainP.add(listL[i]);
    	     //String ret =imgseg.findSegmentClass(fileName+"Histo"+i+".txt");
    	     final String fname = fileName+"Histo"+i+".txt";
    	     if(select == "learn")
    	     {
    	      
    	     //if(ret.equals(""))
    	          //try {
    	        	  File corpus = new File("ClassName");
    	        	  File[] classFileNames = corpus.listFiles();
    	 	    	 //System.out.println(" length : "+classFileNames.length);
    	 			 if(classFileNames.length <= 0)
    	 				 {//System.out.println("here i am at 901");
    	 				 }
    	 	     
    	 			 else if(classFileNames.length > 0)
    	 			 {
    	 				 
    	 				 
    	 				 //className = new Vector();
    	 				 for(int j=0;j<classFileNames.length;j++)  //Collection of category n file per category
    	 			       {
    	 			    	    String name[]; // to remove extension .txt 
    	 			    	    name = classFileNames[j].getName().split("\\.");
    	 			    	    if(i == 1)
    	 			    	    	clsNamesV.add(name[0]);
    	 			    	   jc[i].addItem(name[0]);
    	 			    	   
    	 			            //System.out.println(" Class : "+ i +"  "+name[0]);  
    	 			       }
    	 				 
    	 				 final int ii=i;
    	 				jc[i].addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent arg0) {
								// TODO Auto-generated method stub
								//System.out.println("line 539 : "+jc[ii].getSelectedItem().toString());
								segNameTF[ii].setText(jc[ii].getSelectedItem().toString());
								
							}
						});
    	 				 
    	 			 }
    	          //}//catch(IOException e){};
    	 	    
    	        	  
    	          
    	    	 
    	    	 
    	    	 segNameL[i].setText("Specify Object Class Name : ");
    	    	 comP.add(segNameL[i]);
    	    	 comP.add(jc[i]);
    	    	 comP.add(segNameTF[i]);
    	    	 comP.add(okB[i]);
    	    	 //mainP.add(segNameL[i]);
    	    	 //mainP.add(segNameTF[i]);
    	    	 //mainP.add(okB[i]);
    	    	 segP[i].add(comP);
    	    	 mainP.add(segP[i]);
    	    	 final int indx = i;
    	    	 okB[i].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
					    int flag =0;
						String clsName = segNameTF[indx].getText();
						if(clsNamesV.indexOf(clsName) >= 0) 
							flag =1;
						//System.out.println("line 581 : "+flag);
						new ImageSegmentation().createClass(clsName, fname, flag);
					}
				});
    	    	 
    	     }
               
    	     
    	     
    	     if(select == "class")
    	     {
    	     //if(ret.equals(""))
    	          //try {
    	    	 
    	    	 
    	        //System.out.println("here i am at 610 inside Class");
    	        File corpus = new File("ClassName");
	        	  File[] classFileNames = corpus.listFiles();
		        if(classFileNames.length <= 0)
				 {
		        	//System.out.println("here i am at 901");
		        	}
	     
			 else if(classFileNames.length > 0)
			 {
				  
				 //className = new Vector();
				 for(int j=0;j<classFileNames.length;j++)  //Collection of category n file per category
			       {
			    	    String name[]; // to remove extension .txt 
			    	    name = classFileNames[j].getName().split("\\.");
			    	    if(i == 1)
			    	    	clsNamesV.add(name[0]);
			    	   jc[i].addItem(name[0]);
			    	   
			            //System.out.println(" Class : "+ i +"  "+name[0]);  
			       }
				     	 				     	 				 
				 final int ii=i;
				 
				 jc[i].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						//System.out.println("line 539 : "+jc[ii].getSelectedItem().toString());
						segNameTF[ii].setText(jc[ii].getSelectedItem().toString());
						
					}
				});
				 
			 }
        	
    	              int ret =imgseg.findSegmentClass(fileName+"Histo"+i+".txt");
    	        	  if(ret != -1)
    	        	  {
    	        		//System.out.println("line 652 : "+clsNamesV.size());
    	        		opClassNameL[i] = new JLabel();
    	        		opClassNameL[i].setForeground(fgColor);
    	    	        opClassNameL[i].setFont(new Font("Times new Roman",Font.BOLD,22));
    	    	        
    	    	        inCorrectB[i] = new JButton("Incorrect");
    	    	        inCorrectB[i].setFont(new Font("Times new Roman",Font.ITALIC,12));
    	    	        inCorrectB[i].setBackground(bgColor);
    	    	        inCorrectB[i].setForeground(fgColor);
    	    	        inCorrectB[i].setBorderPainted(false);
    	    	        inCorrectB[i].setFocusable(false);
    	    	 		
    	    	        //System.out.println("after1 "+ clsNamesV.get(ret).toString()+ "return : " +ret);
    	    	        //System.out.println("after2 "+ clsNamesV.get(ret).toString()+ "return : " +ret);
    	    	        opClassNameL[i].setText("Class Name : "+ clsNamesV.get(ret).toString());
    	        		comP.add(opClassNameL[i]);
    	        		
    	        		segInfo[i-1]=clsNamesV.get(ret).toString();
    	        		try{
    	        		BufferedReader clsInfoFR = new BufferedReader(new FileReader("ClassInfo/"+clsNamesV.get(ret).toString
()+"Info"+".txt"));
    	        		String rlinee = clsInfoFR.readLine();
    	        		clsInfoFR.close();
    	        		BufferedWriter clsInfoFW = new BufferedWriter(new FileWriter("ClassInfo/"+clsNamesV.get(ret).toString
()+"Info"+".txt"));
    	        		String clsValue[]=rlinee.split("\\s");  // file values 1.Total 2.Actual 3.Correct 4.Incorrect
    	        		int Total =Integer.parseInt(clsValue[0])+1;
    	        		int Actual = Integer.parseInt(clsValue[1])+1;
    	        		int Correct = Integer.parseInt(clsValue[2])+1;
    	        		clsInfoFW.write(Total+" "+Actual+" "+Correct+" "+clsValue[3]);
    	        		clsInfoFW.close();
    	        		}catch(IOException ie){};
    					//source.close();
    					//clsNameWF.close()
    	        		comP.add(inCorrectB[i]);
    	        		
    	        	  }
    	 	    	 //System.out.println(" length : "+classFileNames.length);
    	 			   //}//catch(IOException e){};
    	 	    
    	        	  
    	          
    	    	 
    	    	 
    	    	 segNameL[i].setText(" Object Class Name : ");
    	    	 comP.add(segNameL[i]);
    	    	 comP.add(jc[i]);
    	    	 comP.add(segNameTF[i]);
    	    	 comP.add(okB[i]);
    	    	 //mainP.add(segNameL[i]);
    	    	 //mainP.add(segNameTF[i]);
    	    	 //mainP.add(okB[i]);
    	    	 segP[i].add(comP);
    	    	 mainP.add(segP[i]);
    	    	
    	    	 segNameL[i].setVisible(false);
    	    	 jc[i].setVisible(false);
    	    	 segNameTF[i].setVisible(false);
    	    	 okB[i].setVisible(false);
    	    	 
    	    	 
    	    	 
    	    	 final int indx = i;
    	    	 if(ret != -1)
    	    	   inCorrectB[i].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						
						try{
	    	        		BufferedReader clsInfoFR = new BufferedReader(new FileReader("ClassInfo/"+segInfo[indx-1]+"Info"+".txt"));
	    	        		String rlinee = clsInfoFR.readLine();
	    	        		clsInfoFR.close();
	    	        		BufferedWriter clsInfoFW = new BufferedWriter(new FileWriter("ClassInfo/"+segInfo[indx-1]+"Info"+".txt"));
	    	        		String clsValue[]=rlinee.split("\\s");  // file values 1.Total 2.Actual 3.Correct 4.Incorrect
	    	        		int Total =Integer.parseInt(clsValue[0]);
	    	        		int Actual = Integer.parseInt(clsValue[1])-1;
	    	        		int Correct = Integer.parseInt(clsValue[2])-1;
	    	        		int Incorrect = Integer.parseInt(clsValue[3])+1;
	    	        		clsInfoFW.write(Total+" "+Actual+" "+Correct+" "+Incorrect);
	    	        		clsInfoFW.close();
	    	        		}catch(IOException ie){};
	    					
						
						
						segNameL[indx].setVisible(true);
		    	    	 jc[indx].setVisible(true);
		    	    	 segNameTF[indx].setVisible(true);
		    	    	 okB[indx].setVisible(true);
		    	    	 
					}
				});
    	    	 
    	    	 
    	    	 okB[i].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
					    int flag =0;
						String clsName = segNameTF[indx].getText();
						if(clsNamesV.indexOf(clsName) >= 0) 
							flag =1;
						
						try{
	    	        		BufferedReader clsInfoFR = new BufferedReader(new FileReader("ClassInfo/"+clsName+"Info"+".txt"));
	    	        		String rlinee = clsInfoFR.readLine();
	    	        		clsInfoFR.close();
	    	        		BufferedWriter clsInfoFW = new BufferedWriter(new FileWriter("ClassInfo/"+clsName+"Info"+".txt"));
	    	        		String clsValue[]=rlinee.split("\\s");  // file values 1.Total 2.Actual 3.Correct 4.Incorrect
	    	        		int Total =Integer.parseInt(clsValue[0]);
	    	        		int Actual = Integer.parseInt(clsValue[1])+1;
	    	        		int Correct = Integer.parseInt(clsValue[2]);
	    	        		int Incorrect = Integer.parseInt(clsValue[3]);
	    	        		clsInfoFW.write(Total+" "+Actual+" "+Correct+" "+Incorrect);
	    	        		clsInfoFW.close();
	    	        		}catch(IOException ie){};
	    				
						
						
						//System.out.println("line 581 : "+flag);
						new ImageSegmentation().createClass(clsName, fname, flag);
					}
				});
    	    	 
    	     }
    	     
    	     
    	     
		}
         img = Toolkit.getDefaultToolkit().getImage(fileName+"ClrBG.jpg");
	     icon = new ImageIcon(img);
	     listL[0]=new JLabel();
	     listL[0].setIcon(icon);
	     
	     //System.out.println("here i am at 3");
	     
	     //mainP.add(listL[0]);   // background image
         
	     //main
         JScrollPane jsp = new JScrollPane(mainP);
         con.add(jsp,BorderLayout.CENTER);
         imgSegmentMainFrame.addWindowListener(new WindowListener() 
 		{
 			public void windowIconified(WindowEvent arg0) {}
 			public void windowDeiconified(WindowEvent arg0) {}
 			public void windowDeactivated(WindowEvent arg0) {}
 			public void windowClosing(WindowEvent arg0) {}
 			public void windowOpened(WindowEvent arg0) {}
 			
 			public void windowClosed(WindowEvent arg0)
 			{
 						imgSegmentMainGUI();
 			}
 			
 			public void windowActivated(WindowEvent arg0) {}
 		});
         
        // emptyL1=new JLabel("   "); 
         //con.add(emptyL1,BorderLayout.SOUTH);
	}
	 
    
    public void perEvaluationGUI()
	{
		perEvaluationFrame = new JFrame("A SALIENT REGION EXTRACTION BASED"+ " ON COLOUR AND TEXTURE FEATURES");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		//Image img = Toolkit.getDefaultToolkit().getImage("src/logol.jpg");
		//imgSegmentMainFrame.setIconImage(img);
		//autoPRGInitalFrame.setName("Automatic Image Data Mining And Segmentaion");
		perEvaluationFrame.setBounds(0, 0, (int) dim.getWidth(),(int) dim.getHeight()-30);
		//perEvaluationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		perEvaluationFrame.setVisible(true);
		
		perEvaluationFrame.setResizable(true);
		//setForeground(fgColor);
		final Container con = perEvaluationFrame.getContentPane(); 
		con.setBackground(bgColor);//238,221,236));//199,217,241));
				
		logoL = new JLabel();
		logoP = new JPanel(); 
		
		nameL = new JLabel("A SALIENT REGION EXTRACTION BASED"+ " ON COLOUR AND TEXTURE FEATURES");
		nameP = new JPanel();
		nameL.setFont(new Font("Times new Roman",Font.ITALIC,30));
		nameP.add(nameL);
		nameP.setBackground(bgColor);//199,217,241));//131,156,153));
		nameL.setForeground(fgColor);//new Color(15,41,74));
		con.add(nameP);
     
		//con.add(new JLabel(""));
         
       File corpus = new File("ClassName");
  	   File[] classNames = corpus.listFiles();
  	   String [] clsname = new String[classNames.length];
  	 perEvaluationFrame.setLayout(new GridLayout(classNames.length*2+1,1));
  	   
  	   //if(classNames.length <= 0)
		// {System.out.println("here i am at 862");}
  	 JLabel[] listL =new JLabel[classNames.length];
  	 JPanel [] listP=new JPanel[classNames.length];
  	 float sumTotal=0, sumCorrect=0;
  	 
	 if(classNames.length > 0)
	 {
		  
		 //className = new Vector();
		 for(int j=0;j<classNames.length;j++)  //Collection of category n file per category
	       {
	    	    String name[]; // to remove extension .txt 
	    	    name = classNames[j].getName().split("\\.");
	    	    clsname[j]= name[0];
	            listL[j] = new JLabel(name[0]);
	            listL[j].setForeground(fgColor);
	            listL[j].setFont(new Font("Times new Roman",Font.BOLD,21));
	            
	            double Recall;
	            double Precision;
	            String Recallstr= "";
	            String Precisionstr="";
	            try{
	            	//System.out.println("line 882"+name[0]);
	        		BufferedReader clsInfoFR = new BufferedReader(new FileReader("ClassInfo/"+name[0]+"Info"+".txt"));
	        		String rlinee = clsInfoFR.readLine();
	        		clsInfoFR.close();
	        		String clsValue[]=rlinee.split("\\s");  // file values 1.Total 2.Actual 3.Correct 4.Incorrect
	        		float Total =Integer.parseInt(clsValue[0]);
	        		float Actual = Integer.parseInt(clsValue[1]);
	        		float Correct = Integer.parseInt(clsValue[2]);
	        		float Incorrect = Integer.parseInt(clsValue[3]);
	        		sumTotal = sumTotal+Total;
	        		sumCorrect = sumCorrect + Correct;
	        		
	        		//System.out.println(Total+" " + Actual+" "+Correct);
	        		 Recall = ((double)(Correct/Actual))*100;
	        		 Recallstr = "----       Recall = "+Recall+"%"; 
	        		 //System.out.println((float)(Correct/Actual));
	                Precision = ((double)(Correct/Total))*100;
	                Precisionstr = "    Precision = "+Precision+"%"; 
	               }catch(IOException ie){};
	            
	    	    listP[j] = new JPanel();
	    	    JLabel recallL = new JLabel(Recallstr);
	    	    recallL.setFont(new Font("Times new Roman",Font.PLAIN,18));
	    	    recallL.setForeground(fgColor);
	    	    JLabel precisionL = new JLabel(Precisionstr);
	    	    precisionL.setFont(new Font("Times new Roman",Font.PLAIN,18));
	    	    precisionL.setForeground(fgColor);
	    	    
	    	    listP[j].add(listL[j]);
	    	    listP[j].add(recallL);
	    	    listP[j].add(precisionL);
	    	    listP[j].setBackground(bgColor);
	    	    listP[j].setForeground(fgColor);
	    	    
	    	    con.add(listP[j]);
	            //System.out.println(" Class : "+ i +"  "+name[0]);  
	       }

		   JPanel accuracyP = new JPanel();
		   float Accuracy = (sumCorrect/sumTotal)*100;
		   
		   JLabel accuracyL = new JLabel("Classifier Accuracy  = " + Accuracy+"%");
		   accuracyL.setFont(new Font("Times new Roman",Font.PLAIN,18));
		   accuracyP.add(accuracyL);
		   accuracyL.setBackground(bgColor);
		   accuracyL.setForeground(fgColor);
		   accuracyP.setForeground(fgColor);
		   accuracyP.setBackground(bgColor);
		   con.add(accuracyP);
		   
		
		perEvaluationFrame.addWindowListener(new WindowListener() 
 		{
 			public void windowIconified(WindowEvent arg0) {}
 			public void windowDeiconified(WindowEvent arg0) {}
 			public void windowDeactivated(WindowEvent arg0) {}
 			public void windowClosing(WindowEvent arg0) {}
 			public void windowOpened(WindowEvent arg0) {}
 			
 			public void windowClosed(WindowEvent arg0)
 			{
 						imgSegmentMainGUI();
 			}
 			
 			public void windowActivated(WindowEvent arg0) {}
 		});
	}
	 
	}
	
	
	
 public static void main(String args[])
 {
	 ImgSegmentaionGUI imgSeg = new ImgSegmentaionGUI();
	 imgSeg.ImgSegmentInitGUI();
 }
}
