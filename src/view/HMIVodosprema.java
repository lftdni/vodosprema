package view;



//import java.awt.List;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;

import kontroler.Kontroler;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.ProgressBar;

public class HMIVodosprema {

	protected Shell shlHmiVodosprema;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			HMIVodosprema window = new HMIVodosprema();
			
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	Kontroler arduino= new Kontroler();	
	//izgradnja objekta arduino - to je zapravo poveznica izmedu viewa i kontrolera
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlHmiVodosprema.open();
		shlHmiVodosprema.layout();
		while (!shlHmiVodosprema.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	 boolean obje = false;
	 //varijabla obje = false je pocetna postavka vrijednosti ventila i pumpe 	 //inace ako su aktivirani vrijednost je true
	/**
	 * Create contents of the window.
	 */
	 
	protected void createContents() {
		shlHmiVodosprema = new Shell();
	
		
		shlHmiVodosprema.setSize(676, 318);
		shlHmiVodosprema.setText("HMI vodosprema");
		final ProgressBar progressBar = new ProgressBar(shlHmiVodosprema, SWT.VERTICAL);
		progressBar.setMaximum(500);
		final Button btnPokreni = new Button(shlHmiVodosprema, SWT.NONE);
		final Label lblAlarm = new Label(shlHmiVodosprema, SWT.NONE);
		lblAlarm.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		lblAlarm.setFont(SWTResourceManager.getFont("Terminal", 27, SWT.BOLD));
		lblAlarm.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		lblAlarm.setAlignment(SWT.CENTER);
		lblAlarm.setText("ALARM");
		
		final Label lblStatus = new Label(shlHmiVodosprema, SWT.BORDER);
		lblStatus.setBackground(SWTResourceManager.getColor(0, 0, 0));
		Canvas canvas = new Canvas(shlHmiVodosprema, SWT.BORDER);
		canvas.setBounds(10, 208, 157, 61);
		final Button btnPlus = new Button(shlHmiVodosprema, SWT.NONE);
		
		final Button btnMinus = new Button(shlHmiVodosprema, SWT.NONE);		
		
		lblStatus.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));		
		lblStatus.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		lblStatus.setText("Trenutno stanje procesa: ");
		lblStatus.setAlignment(SWT.LEFT);			
		
		final Button btnPumpa = new Button(canvas, SWT.CHECK);
		btnPumpa.setEnabled(false);
		btnPumpa.setBounds(10, 10, 93, 16);
		btnPumpa.setText("PUMPA");
		btnPokreni.setBounds(254, 244, 75, 25);
		btnPokreni.setText("Pokreni");
		final Button btnVentil = new Button(canvas, SWT.CHECK);
		btnVentil.setEnabled(false);
		btnVentil.setBounds(10, 31, 93, 16);
		btnVentil.setText("VENTIL");
		
		Label lblKontrolaPumpe = new Label(shlHmiVodosprema, SWT.NONE);
		lblKontrolaPumpe.setBounds(10, 187, 150, 15);
		lblKontrolaPumpe.setText("Upravljanje vodospremom");
		
		Label label = new Label(shlHmiVodosprema, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(10, 170, 275, 11);
		btnMinus.setEnabled(false);
		btnPlus.setEnabled(false);
		final Button btnPosalji = new Button(shlHmiVodosprema, SWT.NONE);
		btnPosalji.setEnabled(false);
		btnPosalji.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean pumpa;
				boolean ventil;
				//varijable koje spremaju vrijednost stanja ocitanog sa tipki za aktiviranje pumpe i ventila 
				int razinaI = arduino.pooling();
				//varijabla razinaI koja poziva funkciju pooling iz kontrolera i od nje dobiva vrijednost razine,
				//tako dobivena vrijednost postavlja se kao vrijednost progressbara 
				progressBar.setSelection(razinaI);
				
				String stanjePumpa;
				String stanjeVentil;
				String razina=String.valueOf(razinaI);
				//varijable stanja pumpe ventila i razine->ispis statusa			
				pumpa=btnPumpa.getSelection();
				ventil=btnVentil.getSelection();
				//------------------------------------------------ocitavanje tipki				
								
				
				MessageBox boks = new MessageBox(shlHmiVodosprema);
				boks.setMessage("Zahtjev je poslan");				
				boks.open();
//-------------------------------blokiranje tipki i ispis poruke zahtjev je poslan,popup boks
				
				if(pumpa == true && razinaI > 0)
				{
					stanjePumpa="Upaljena";
					btnMinus.setEnabled(true);
					
				}
				else
				{
					stanjePumpa="Ugasena";	
					btnMinus.setEnabled(false);
				}
				if (ventil == true && razinaI < 500)
				{
					stanjeVentil="Otvoren";	
					btnPlus.setEnabled(true);
				}
				else
				{
					stanjeVentil="Zatvoren";
				}	
				if (ventil == true && pumpa == true)
				{
					stanjeVentil="Otvoren";
					stanjePumpa="Upaljena";
					btnPlus.setEnabled(true);
					btnMinus.setEnabled(false);
				}
				if (ventil == true && pumpa == true)
				{
					obje= true;
				}
				else
				{
					obje = false;
				}
				
				lblStatus.setText("Trenutno stanje procesa: \nPUMPA: "+stanjePumpa +
															"\nVENTIL: "+stanjeVentil+
															"\nRAZINA: "+razina+" l");
				
//--postavljanje statusa i ispis statusa o procesu,konstantno azuriranje pritiskom na tipku				
							
			}
		});
		btnPosalji.setBounds(173, 244, 75, 25);
		btnPosalji.setText("Posalji");		
		
		lblStatus.setBounds(86, 94, 196, 70);		
		
		btnPlus.setBounds(173, 187, 75, 25);
		btnPlus.setText("+");
		
		
		btnMinus.setBounds(173, 213, 75, 25);
		btnMinus.setText("-");
		
		final Label lblRazina = new Label(shlHmiVodosprema, SWT.NONE);
		lblRazina.setBounds(25, 44, 55, 15);
		lblRazina.setText("Razina");		
				
		
		lblAlarm.setBounds(86, 10, 196, 70);
		lblAlarm.setText("ALARM");
					
		
		final Label lblVrloVisoko = new Label(shlHmiVodosprema, SWT.BORDER);
		lblVrloVisoko.setAlignment(SWT.CENTER);
		lblVrloVisoko.setBounds(300, 29, 125, 43);
		lblVrloVisoko.setText("Vrlo visoko");
		
		final Label lblVisoko = new Label(shlHmiVodosprema, SWT.BORDER);
		lblVisoko.setAlignment(SWT.CENTER);
		lblVisoko.setBounds(300, 78, 125, 43);
		lblVisoko.setText("Visoko");
		
		final Label lblNisko = new Label(shlHmiVodosprema, SWT.BORDER);
		lblNisko.setAlignment(SWT.CENTER);
		lblNisko.setBounds(300, 127, 125, 43);
		lblNisko.setText("Nisko");
		
		final Label lblVrloNisko = new Label(shlHmiVodosprema, SWT.BORDER);
		lblVrloNisko.setAlignment(SWT.CENTER);
		lblVrloNisko.setBounds(300, 186, 125, 47);
		lblVrloNisko.setText("Vrlo nisko");
		
		Label label_1 = new Label(shlHmiVodosprema, SWT.SEPARATOR | SWT.VERTICAL);
		label_1.setBounds(278, 10, 19, 165);
		
		Label label_2 = new Label(shlHmiVodosprema, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_2.setBounds(10, 86, 262, 2);
		
		Label lblRazina_1 = new Label(shlHmiVodosprema, SWT.NONE);
		lblRazina_1.setBounds(10, 10, 55, 15);
		lblRazina_1.setText("Razina:");
		
		Label lblStatus_1 = new Label(shlHmiVodosprema, SWT.NONE);
		lblStatus_1.setBounds(10, 95, 55, 15);
		lblStatus_1.setText("Status:");
		
		
		progressBar.setEnabled(false);
		progressBar.setBounds(459, 19, 142, 237);
		
		Label label_3 = new Label(shlHmiVodosprema, SWT.SEPARATOR | SWT.VERTICAL);
		label_3.setBounds(440, 10, 2, 259);
		
		Label label_4 = new Label(shlHmiVodosprema, SWT.NONE);
		label_4.setBounds(605, 10, 55, 15);
		label_4.setText("500");
		
		Label label_5 = new Label(shlHmiVodosprema, SWT.NONE);
		label_5.setBounds(607, 232, 55, 15);
		label_5.setText("0");
		
		Label label_6 = new Label(shlHmiVodosprema, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_6.setBounds(607, 30, 45, 2);
		
		Label label_7 = new Label(shlHmiVodosprema, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_7.setBounds(605, 249, 45, 12);
		
		btnPlus.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean pumpa;
				boolean ventil;
				
				
				String stanjeVentil;
				String stanjePumpa;
				int razina = arduino.pooling();				
			
				
				pumpa=btnPumpa.getSelection();
				ventil=btnVentil.getSelection();			
				
				if(pumpa==true)
				{
					stanjePumpa="Upaljena";					
				}
				else
				{
					stanjePumpa="Ugasena";						
				}
				if(ventil==true)
				{
					stanjeVentil="Otvoren";						
				}
				else
				{
					stanjeVentil="Zatvoren";
				}	
				if(ventil == true && pumpa == true)
				{
					stanjeVentil="Otvoren";
					stanjePumpa="Upaljena";					
				}
				
				if(obje == true && razina <= 500)
				{
					arduino.upaljenoOtvoreno();
					
					String prikazRazine;
					razina=arduino.pooling();
					progressBar.setSelection(razina);
					prikazRazine=String.valueOf(razina);
					lblRazina.setText(prikazRazine);
					lblStatus.setText("Trenutno stanje procesa: \nPUMPA: "+stanjePumpa +
							"\nVENTIL: "+stanjeVentil+
							"\nRAZINA: "+razina+" l");
					
					arduino.Datotekica(razina);
					
					if(razina >= 450 && razina < 500)
					{
						lblVrloVisoko.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
						lblVrloVisoko.setFont(SWTResourceManager.getFont("Terminal", 17, SWT.BOLD));
						lblVisoko.setBackground(SWTResourceManager.getColor(240,240,240));
						lblVisoko.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
						
						
					}
					
					if(razina >= 300&& razina < 450)
					{
						lblVisoko.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
						lblVisoko.setFont(SWTResourceManager.getFont("Terminal", 17, SWT.BOLD));
						
						
					}
					if(razina == 150)
					{
						lblNisko.setBackground(SWTResourceManager.getColor(240,240,240));
						lblNisko.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
					}
					
											
					
					if(razina>=60 && razina < 150)
					{
						lblNisko.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
						lblNisko.setFont(SWTResourceManager.getFont("Terminal", 17, SWT.BOLD));
						lblVrloNisko.setBackground(SWTResourceManager.getColor(240,240,240));
						lblVrloNisko.setFont(SWTResourceManager.getFont("Segoe UI",9, SWT.NORMAL));
					
						
					}	
					
					if(razina>0 && razina<=50)
					{
						lblVrloNisko.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
						lblVrloNisko.setFont(SWTResourceManager.getFont("Terminal", 17, SWT.BOLD));
						lblNisko.setBackground(SWTResourceManager.getColor(240,240,240));
						lblNisko.setFont(SWTResourceManager.getFont("Segoe UI",9, SWT.NORMAL));
						lblAlarm.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
						lblAlarm.setFont(SWTResourceManager.getFont("Terminal", 27, SWT.BOLD));
						lblAlarm.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
						lblAlarm.setAlignment(SWT.CENTER);
						lblAlarm.setText("ALARM");
						
					}
					
					if(razina>=500)
					{
						lblVrloVisoko.setBackground(SWTResourceManager.getColor(240,240,240));
						lblVrloVisoko.setFont(SWTResourceManager.getFont("Segoe UI",9, SWT.NORMAL));
						lblAlarm.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
						lblAlarm.setFont(SWTResourceManager.getFont("Terminal", 27, SWT.BOLD));
						lblAlarm.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
						lblAlarm.setAlignment(SWT.CENTER);
						lblAlarm.setText("ALARM:\nPUN!!!");
						btnMinus.setEnabled(false);						
						MessageBox boks = new MessageBox(shlHmiVodosprema);
						boks.setMessage("ALARM: Spremnik je pun");				
						boks.open();						
						
						btnVentil.setSelection(false);
						btnPumpa.setSelection(false);
						ventil=false;
						pumpa=false;
						stanjeVentil="Zatvoren";
						stanjePumpa="Ugasena";
												
						btnPlus.setEnabled(false);
						
						
						lblStatus.setText("--SIGURNOSNI PREKID:-- \nPUMPA: "+stanjePumpa +
								"\nVENTIL: "+stanjeVentil+
								"\nRAZINA: "+razina+" l");											
						
/*u svakom ovom if vrsi se provjere pa ako je uvjet zadovoljen odredena labela se pali postaje plava i 
 * uocljiva a ona prethodna se gasi
 * nakon svakog klika uzimaju se vrijednosti provjere i na osnovu provjere
 *  ispisuju se statusi i izmjenjuju svojstva labela */					
						
						
					}
					arduino.kruzniProces();	
//poziva funkciju kruzniproces koja vrsi provjere  i postavlja T i F vrijednosti na senzore ovisno o uvjetu					
					
				}
				
				if( pumpa == false && ventil == true )
				{
					arduino.otvoriVentil(ventil);
					
					String prikazRazine;
					razina=arduino.pooling();
					progressBar.setSelection(razina);
					prikazRazine=String.valueOf(razina);
					lblRazina.setText(prikazRazine);
					lblStatus.setText("Trenutno stanje procesa: \nPUMPA: "+stanjePumpa +
							"\nVENTIL: "+stanjeVentil+
							"\nRAZINA: "+razina+" l");
					
					arduino.Datotekica(razina);
					
					if( razina >= 450 && razina < 500 )
					{
						lblVrloVisoko.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
						lblVrloVisoko.setFont(SWTResourceManager.getFont("Terminal", 17, SWT.BOLD));
						lblVisoko.setBackground(SWTResourceManager.getColor(240,240,240));
						lblVisoko.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));					
						
					}
					
					if( razina >= 300 && razina < 450 )
					{
						lblVisoko.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
						lblVisoko.setFont(SWTResourceManager.getFont("Terminal", 17, SWT.BOLD));						
						
					}
					if( razina >= 150 )
					{
						lblNisko.setBackground(SWTResourceManager.getColor(240,240,240));
						lblNisko.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
					}			
							
					
					if( razina >=60 && razina < 150 )
					{
						lblNisko.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
						lblNisko.setFont(SWTResourceManager.getFont("Terminal", 17, SWT.BOLD));
						lblVrloNisko.setBackground(SWTResourceManager.getColor(240,240,240));
						lblVrloNisko.setFont(SWTResourceManager.getFont("Segoe UI",9, SWT.NORMAL));					
						
					}	
					
					if( razina >0 && razina <= 50 )
					{
						lblVrloNisko.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
						lblVrloNisko.setFont(SWTResourceManager.getFont("Terminal", 17, SWT.BOLD));
						lblNisko.setBackground(SWTResourceManager.getColor(240,240,240));
						lblNisko.setFont(SWTResourceManager.getFont("Segoe UI",9, SWT.NORMAL));
						lblAlarm.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
						lblAlarm.setFont(SWTResourceManager.getFont("Terminal", 27, SWT.BOLD));
						lblAlarm.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
						lblAlarm.setAlignment(SWT.CENTER);
						lblAlarm.setText("ALARM");
						
					}
					
					if( razina >= 500 )
					{
						lblVrloVisoko.setBackground(SWTResourceManager.getColor(240,240,240));
						lblVrloVisoko.setFont(SWTResourceManager.getFont("Segoe UI",9, SWT.NORMAL));
						lblAlarm.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
						lblAlarm.setFont(SWTResourceManager.getFont("Terminal", 27, SWT.BOLD));
						lblAlarm.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
						lblAlarm.setAlignment(SWT.CENTER);
						lblAlarm.setText("ALARM:\nPUN!!!");
						btnMinus.setEnabled(false);
						MessageBox boks = new MessageBox(shlHmiVodosprema);
						boks.setMessage("ALARM: Spremnik je pun");				
						boks.open();
						btnPlus.setEnabled(false);				
						
						
						btnVentil.setSelection(false);
						ventil=false;
						stanjeVentil="Zatvoren";
						btnPumpa.setSelection(false);
						pumpa=false;
						btnPlus.setEnabled(false);
						stanjePumpa="Zatvoren";
						
						lblStatus.setText("--SIGURNOSNI PREKID:-- \nPUMPA: "+stanjePumpa +
								"\nVENTIL: "+stanjeVentil+
								"\nRAZINA: "+razina+" l");
						
					}
					
					arduino.kruzniProces();
					
				}
		}
		});

		
		btnPokreni.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				
				btnPumpa.setEnabled(true);
				btnPosalji.setEnabled(true);
				btnVentil.setEnabled(true);
				arduino.UcitajDat();
				int razinaI=arduino.pooling();
				String razina=String.valueOf(razinaI);				
				lblRazina.setText(razina);
				btnPokreni.setEnabled(false);
				progressBar.setSelection(razinaI);
				
				if(razinaI >= 450 && razinaI < 500)
				{
					lblVrloVisoko.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					lblVrloVisoko.setFont(SWTResourceManager.getFont("Terminal", 17, SWT.BOLD));
					lblVisoko.setBackground(SWTResourceManager.getColor(240,240,240));
					lblVisoko.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
					
					
				}
				
				if(razinaI >= 300&& razinaI < 450)
				{
					lblVisoko.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					lblVisoko.setFont(SWTResourceManager.getFont("Terminal", 17, SWT.BOLD));
					
					
				}
				if(razinaI >= 150)
				{
					lblNisko.setBackground(SWTResourceManager.getColor(240,240,240));
					lblNisko.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
				}
				
										
				
				if(razinaI>=60 && razinaI < 150)
				{
					lblNisko.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					lblNisko.setFont(SWTResourceManager.getFont("Terminal", 17, SWT.BOLD));
					lblVrloNisko.setBackground(SWTResourceManager.getColor(240,240,240));
					lblVrloNisko.setFont(SWTResourceManager.getFont("Segoe UI",9, SWT.NORMAL));
				
					
				}	
				
				if(razinaI>0 && razinaI<=50)
				{
					lblVrloNisko.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					lblVrloNisko.setFont(SWTResourceManager.getFont("Terminal", 17, SWT.BOLD));
					lblNisko.setBackground(SWTResourceManager.getColor(240,240,240));
					lblNisko.setFont(SWTResourceManager.getFont("Segoe UI",9, SWT.NORMAL));
					lblAlarm.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
					lblAlarm.setFont(SWTResourceManager.getFont("Terminal", 27, SWT.BOLD));
					lblAlarm.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
					lblAlarm.setAlignment(SWT.CENTER);
					lblAlarm.setText("ALARM");
					
				}
				
				if(razinaI>=500)
				{
					lblVrloVisoko.setBackground(SWTResourceManager.getColor(240,240,240));
					lblVrloVisoko.setFont(SWTResourceManager.getFont("Segoe UI",9, SWT.NORMAL));
					lblAlarm.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					lblAlarm.setFont(SWTResourceManager.getFont("Terminal", 27, SWT.BOLD));
					lblAlarm.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					lblAlarm.setAlignment(SWT.CENTER);
					lblAlarm.setText("ALARM:\nPUN!!!");
					btnMinus.setEnabled(false);
					MessageBox boks = new MessageBox(shlHmiVodosprema);
					boks.setMessage("ALARM: Spremnik je pun");				
					boks.open();
					btnPlus.setEnabled(false);
				}
				
			
//------tipka za pokretanje pokrece funkciju za izgradivanje objekata
			}
		});
		
		
		btnMinus.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean pumpa;
				boolean ventil;
				int razina= arduino.pooling();
				String stanjeVentil;
				String stanjePumpa;
				pumpa=btnPumpa.getSelection();
				ventil=btnVentil.getSelection();
				progressBar.setSelection(razina);
				if(pumpa==true)
				{
					stanjePumpa="Upaljena";					
				}
				else
				{
					stanjePumpa="Ugasena";						
				}
				if(ventil==true)
				{
					stanjeVentil="Otvoren";						
				}
				else
				{
					stanjeVentil="Zatvoren";
				}	
				if(ventil == true && pumpa == true)
				{
					stanjeVentil="Otvoren";
					stanjePumpa="Upaljena";					
				}
				
				
				if(pumpa==true && ventil==false)
				{
					arduino.upaliPumpu(pumpa);
					String prikazRazine;
					razina=arduino.pooling();
					arduino.Datotekica(razina);
					
					if(razina >= 450 && razina < 500)
					{
						lblVrloVisoko.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
						lblVrloVisoko.setFont(SWTResourceManager.getFont("Terminal", 17, SWT.BOLD));
						
						
						lblAlarm.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
						lblAlarm.setFont(SWTResourceManager.getFont("Terminal", 27, SWT.BOLD));
						lblAlarm.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
						lblAlarm.setAlignment(SWT.CENTER);
						lblAlarm.setText("ALARM");
					}
					
					if(razina >= 310 && razina < 450)
					{
						lblVisoko.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
						lblVisoko.setFont(SWTResourceManager.getFont("Terminal", 17, SWT.BOLD));
						lblVrloVisoko.setBackground(SWTResourceManager.getColor(240,240,240));
						lblVrloVisoko.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
						
					}
					if(razina <= 300)
					{
						lblVisoko.setBackground(SWTResourceManager.getColor(240,240,240));
						lblVisoko.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
					}
					
											
					
					if(razina>=60 && razina <= 150)
					{
						lblNisko.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
						lblNisko.setFont(SWTResourceManager.getFont("Terminal", 17, SWT.BOLD));
						
					
						
					}	
					
					if(razina>0 && razina<=50)
					{
						lblVrloNisko.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
						lblVrloNisko.setFont(SWTResourceManager.getFont("Terminal", 17, SWT.BOLD));
						lblNisko.setBackground(SWTResourceManager.getColor(240,240,240));
						lblNisko.setFont(SWTResourceManager.getFont("Segoe UI",9, SWT.NORMAL));
						
					}
					prikazRazine=String.valueOf(razina);
					lblRazina.setText(prikazRazine);
					lblStatus.setText("Trenutno stanje procesa: \nPUMPA: "+stanjePumpa +
							"\nVENTIL: "+stanjeVentil+
							"\nRAZINA: "+razina+" l");
					if(razina<=0)
					{
						prikazRazine=String.valueOf(razina);
					lblRazina.setText(prikazRazine);
					lblStatus.setText("Trenutno stanje procesa: \nPUMPA: "+stanjePumpa +
							"\nVENTIL: "+stanjeVentil+
							"\nRAZINA: "+razina+" l");
					lblVrloNisko.setBackground(SWTResourceManager.getColor(240,240,240));
					lblVrloNisko.setFont(SWTResourceManager.getFont("Segoe UI",9, SWT.NORMAL));
						lblAlarm.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
						lblAlarm.setFont(SWTResourceManager.getFont("Terminal", 27, SWT.BOLD));
						lblAlarm.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
						lblAlarm.setAlignment(SWT.CENTER);
						lblAlarm.setText("ALARM:\nPRAZAN!!!");
						btnMinus.setEnabled(false);
						MessageBox boks = new MessageBox(shlHmiVodosprema);
						boks.setMessage("ALARM: Spremnik je prazan");				
						boks.open();
						
						btnPumpa.setSelection(false);
						pumpa=false;
						stanjePumpa="Ugasena";					
						
						
						
						lblStatus.setText("--SIGURNOSNI PREKID:-- \nPUMPA: "+stanjePumpa +
								"\nVENTIL: "+stanjeVentil+
								"\nRAZINA: "+razina+" l");	
					}
					
					arduino.kruzniProces();
					
					
					

				}
				
				
			}
		});
	}
}
