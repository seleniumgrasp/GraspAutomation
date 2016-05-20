package commonFunctions;

//import io.selendroid.exceptions.NoSuchElementException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import Driver.Driver_commonObjects;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.google.common.base.Function;

import Utilities.Log;


public class Functions {

	private final Logger logger = Log.getInstance(Thread.currentThread()
			.getStackTrace()[1].getClassName());
	public static WebDriver driver;
	public boolean flag=false;



	/* Method Name : --- ----- --- getDriver
	 * Purpose 	   	: --- ----- --- Returns the driver for HTML Reporter class
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public WebDriver getDriver() {
		return driver;
	}



	/* ******************************************************************************/
	/* Method Name : --- ----- --- isElementPresent
	 * Purpose 	   	: --- ----- --- verify the webelement presence	
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public boolean isElementPresent(By byValue){
		flag=false;
		try
		{
			driver.findElement(byValue);
			flag = true;
		}catch(Exception ObjErr){
			Reporter.log("Object not found and object byValue is" +byValue);
			flag= false;
		}
		return flag;
	}


	/* ******************************************************************************/
	/* Method Name : --- ----- --- getAppTitle
	 * Purpose 	   	: --- ----- --- Get Application title
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public String getAppTitle(){
		return driver.getTitle();
	}



	/* ******************************************************************************/
	/* Method Name : --- ----- --- clearWebEdit
	 * Purpose 	   	: --- ----- --- initiate the android driver
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void clearWebEdit(By by){			
		driver.findElement(by).clear();

	}

	/* ******************************************************************************/
	/* Method Name : --- ----- --- Sleep
	 * Purpose 	   	: --- ----- --- Sleep for specified time
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public void sleep(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	/* ******************************************************************************/
	/* Method Name : --- ----- --- fluentWait
	 * Purpose 	   	: --- ----- --- wait for the element
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public WebElement fluentWait(final By locator) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		});
		return foo;
	}



	/* *******************************************************************************/

	/* Method Name : --- ----- --- getText
	 * Purpose 	   	: --- ----- --- retrive the text from the element
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public String getText(By by){
		String ActualText=driver.findElement(by).getText();
		return ActualText;
	}
	
	/* *******************************************************************************/

	/* Method Name : --- ----- --- getText
	 * Purpose 	   	: --- ----- --- retrive the text from the element
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public String getText(WebElement ele){
		String ActualText=ele.getText();
		return ActualText;
	}
	
	/* *******************************************************************************/

	/* Method Name : --- ----- --- getText
	 * Purpose 	   	: --- ----- --- retrive the text from the element
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public String getTextfromList(By by, int i){
		List<WebElement> ele = listofelements(by);
		String ActualText=ele.get(i-1).getText();
		return ActualText;
	}
	

	/* ******************************************************************************/

	/* Method Name : --- ----- --- getAndVerifyTextattribute
	 * Purpose 	   	: --- ----- --- get and verify the text of the webelement
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public boolean getAndVerifyTextvalue(By by,String expText){
		flag=false;
		String actualText=driver.findElement(by).getAttribute("value");
		if(actualText.equalsIgnoreCase(expText))
		{
			flag=true;
		}
		return flag;
	}

	/* ******************************************************************************/

	/* Method Name : --- ----- --- getDriver
	 * Purpose 	   	: --- ----- --- get the text and return the text
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public String getTextandreturn(By by){
		String str;
		str=driver.findElement(by).getText();
		return str;
	}

	/* ******************************************************************************/

	/* Method Name : --- ----- --- getAndVerifyText
	 * Purpose 	   	: --- ----- --- get and verify the text of the webelement
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public boolean getAndVerifyText(By by,String expText){
		flag=false;
		String actualText=driver.findElement(by).getText();
		if(actualText.equalsIgnoreCase(expText))
		{
			flag=true;
		}
		return flag;
	}
	
	/* Method Name : --- ----- --- getAndVerifyTextfromList
	 * Purpose 	   	: --- ----- --- get and verify the text of the list of webelement
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public boolean getAndVerifyTextfromList(By by,String expText, int j){
		flag=false;
		List<WebElement> ele = driver.findElements(by);
				System.out.println(ele.get(j-1).getText());
				if(ele.get(j-1).getText().contains(expText))
		{
			System.out.println(ele.get(j-1).getText()+" is present");
			flag=true;
		}
		
		return flag;
	}



	/* ******************************************************************************/

	/* Method Name : --- ----- --- getAndVerifyPartialText
	 * Purpose 	   	: --- ----- --- get and verify the Partial text of the web element
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public boolean getAndVerifyPartialText(By by,String expText)
	{
		flag=false;
		String actualText=driver.findElement(by).getText();
		if(actualText.contains(expText))
		{
			flag=true;
		}
		return flag;
	}
	
	/* ******************************************************************************/

	/* Method Name : --- ----- --- getAndVerifyPartialText
	 * Purpose 	   	: --- ----- --- get and verify the Partial text of the web element
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public boolean getAndVerifyPartialText(WebElement ele,String expText)
	{
		flag=false;
		String actualText=ele.getText();
		if(actualText.contains(expText))
		{
			flag=true;
		}
		return flag;
	}

	/* ******************************************************************************/
	/* Method Name : --- ----- --- getAndVerifyPartialPrice
	 * Purpose 	   	: --- ----- --- get and verify the Partial Price of the web element
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void getAndVerifyPartialprice(By by,String expText)
	{
		waitforElementVisible(by);
		int j=0;
		char[] exp = expText.toCharArray();
		System.out.println(exp.length);
		String actualText=driver.findElement(by).getText();
		char[] act = actualText.toCharArray();
		System.out.println("Actual Text "+ actualText);
		System.out.println("Expected Text "+ expText);
		Reporter.log("Actual Quantity" +actualText);
		if(exp.length==act.length){

			for(int i=2; i<exp.length; i++){
				if(exp[i]==act[i]){
					System.out.println(exp[i]+" and  "+act[i]);
					j=i;
				}break;

				//continue;
			}
			if(j == exp.clone().length){
				System.out.println("both "+actualText+" and "+expText+" not equal");
			}else{

			}

		}

	}

	/* ******************************************************************************/
	/* Method Name : --- ----- --- getAndVerifyPartialPrice1
	 * Purpose 	   	: --- ----- --- get and verify the Partial Price of the web element
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void getAndVerifyPartialprice1(By by,String expText)
	{

		waitforElementVisible(by);
		char[] exp = expText.toCharArray();
		System.out.println(exp.length);
		int i= exp.length;
		String actualText=driver.findElement(by).getText();
		char[] act = actualText.toCharArray();
		System.out.println("Actual Text "+ actualText);
		System.out.println("Expected Text "+ expText);

		if(exp.length==act.length){
			if(expText.substring(2, i).contains(actualText.substring(2, i))){
				System.out.println("both "+actualText+" and "+expText+" equal");
			}else{
				System.out.println("both "+actualText+" and "+expText+" not equal");
			}
		}
		Reporter.log("Actual Quantity" +actualText);
	}



	/* ******************************************************************************/

	/* Method Name : --- ----- --- verifyWebElementAnyPage
	 * Purpose 	   	: --- ----- --- verify the webelement
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public void verifyWebElementAnyPage(By by, String expTest)
	{

		fluentWait(by);	
		String actualText=driver.findElement(by).getText();		
		System.out.println(actualText);
		try{
			Assert.assertEquals(actualText, expTest);
			//return true;
		}catch(Throwable e){

			org.testng.Assert.fail("expected and actual result do not match");
			//return false;
			// throw e;

		}


	}



	/* ******************************************************************************/

	/* Method Name : --- ----- --- sendKeys
	 * Purpose 	   : --- ----- --- enter the value
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void sendKeys(By by,String text){
		//clearWebEdit(by);
		WebElement Send=driver.findElement(by);
		highlight(Send);
		Send.sendKeys(text);
	}



	/* ******************************************************************************/

	/* Method Name : --- ----- --- click
	 * Purpose 	   : --- ----- --- click on the  element
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void click(By by) {
		waitForElement(by);
		fluentWait(by);
		WebElement cli=driver.findElement(by);
		highlight(cli);
		cli.click();
		Reporter.log("clicked on the corresponding element -" +by);
		waitForPageToLoad(40);
	}
	
	/* ******************************************************************************/

	/* Method Name : --- ----- --- clickbylinktext 
	 * Purpose 	   : --- ----- --- click on the  element by link text
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void clickbylinktext(String linktxt) {
		WebElement cli=driver.findElement(By.linkText(linktxt));
		highlight(cli);
		cli.click();
		Reporter.log("clicked on the corresponding element by link text -" +linktxt);
		waitForPageToLoad(40);
	}




	/* ******************************************************************************/

	/* Method Name : --- ----- --- selectListValue
	 * Purpose 	   : --- ----- --- select the value from the dropdown list
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void selectListValue(By by,String text) {
		waitForElement(by);
		WebElement select=driver.findElement(by);
		waitForPageToLoad(15);
		List<WebElement> options=select.findElements(By.tagName("option"));

		for (WebElement option:options){
			System.out.println(option.getText());

			if (option.getText().equalsIgnoreCase(text)){									
				option.click();
				fluentWait(by);
				Reporter.log("Selected the item "+text);
				break;
			}
		}
	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- selectlist
	 * Purpose 	   : --- ----- ---  select the list value from dropdown
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void selectList(By by,String num){
		System.out.println("List value to be selected "+num);

		Select lst=new Select(driver.findElement(by));
		lst.selectByVisibleText(num);
	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- verifyListValue
	 * Purpose 	   : --- ----- ---  verify the list value
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public void verifyListValue(By by,String text){

		Select lst=new Select(driver.findElement(by));
		String lstValue=lst.getFirstSelectedOption().getText();
		if (lstValue.equalsIgnoreCase(text)){
			Reporter.log("Item "+text+" has selected");
		}else
			org.testng.Assert.fail("Item "+text+" has not selected" );
		//Reporter.log("Item "+text+" has not selected");
	}



	/* ******************************************************************************/

	/* Method Name : --- ----- --- verifyTextPresent
	 * Purpose 	   : --- ----- ---  verify the text present
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public boolean verifyTextPresent(By by,String expText,String Actual)
	{
		fluentWait(by);
		String actText=driver.getPageSource();
		if(actText.contains(expText))
		{
			System.out.println(expText+" is matched with the"+Actual);
			
			flag=true;
		}
if (flag=true)
{
	Reporter.log("PASS_MESSAGE:-"+ expText+" is matched with the"+Actual);
	return flag;
}
else{
		Reporter.log("FAIL MESSAGE:-"+ expText+" is not matched with the"+Actual);
}
		return flag;
	}



	/* ******************************************************************************/
	/* Method Name : --- ----- --- VerifyListValueStreettype
	 * Purpose 	   : --- ----- ---  verify the text present
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void VerifyListValueStreettype(){

		Select listbox=new Select(driver.findElement(By.cssSelector("#StreetType")));
		List<WebElement> options=listbox.getOptions();

		String listvalue="Indirizzo;ACCESSO;ACCORCIATOIA;AEROPORTO;AIUOLA;ALTO;ALZAIA;ANDRONA;ANGIPORTO;APERTA;ARCHI;ARCHIVOLTO;ARCO;ARGINE;ARGINE DESTRO;ASSE;ATRIO;AUTOPORTO;AUTOSTRADA;AVENUE;BACINO;BAGLIO;BALUARDO;BANCHINA;BARBARIA;BASTIONE;BASTIONI;BELVEDERE;BIVIO;BLOCCO;BOCCA;BORGATA;BORGHETTO;BORGO;BORGOLOCO;CA;CALA;CALATA;CALLE;CALLE LARGA;CALLETTA;CAMMINO;CAMPAZZO;CAMPIELLO;CAMPO;CANALE;CANCELLO;CANTO;CANTONE;CAPO;CARRAIA;CARRARA;CARRARO;CARRARONE;CARRIZZADA;CASA;CASALE;CASCINA;CASCINE;CASE;CASE SPARSE;CASEGGIATO;CASELLO;CASENUOVE;CASINA;CAVA;CAVALCAVIA;CAVATO;CAVONE;CENTRO;CENTRO COMMERCIALE;CHIASSETTO;CHIASSO;CHIESA;CHIOSTRA;CHIOSTRI;CHIOSTRO;CIRCONVALLAZIONE;CIRCONVALLAZIONE STATALE;CLAUSTRO;CLIVO;COLLE;COLLINA;COMPLANARE;COMPLESSO;COMPRENSORIO;CONTRA;CONTRADA;CONTRADA CHIUSA;CONTRADA PIANA;CORSETTO;CORSIA;CORSO;CORTE;CORTE PRIVATA;CORTI;CORTICELLA;CORTILE;COSTA;CROCEVIA;CROSA;CROSINO;CUPA;CUPA VICINALE;DARSENA;DEPOSITO;DIGA;DIRAMAZIONE;DISCESA;DISTACCO;EMICICLO;ERTA;ESEDRA;ESTRAMURALE;EXTRAURBANA;FONDACO;FONDACO I;FONDAMENTA;FONDO;FONTEGO;FORNICE;FORO;FORTE;FOSSA;FOSSATO;FOSSO;FRAZIONE;GALLERIA;GIARDINI;GIARDINO;GRADINATA;GRADINI;GRADONI;GRAN VIALE;GUASTI;INTERNO;INTERRATO;ISOLA;LARGHETTO;LARGO;LARGO II;LARGO PRIVATO;LARGO PROPRIO;LATERALE;LATERALE NORD;LATERALE SUD;LIDO;LITORANEA;LOCALITA;LOGGE;LOGGIA;LOTTIZZAZIONE;LUNGADIGE;LUNGARGINE;LUNGARNO;LUNGO;LUNGO TALVERA;LUNGOADDA;LUNGOBISAGNO;LUNGOBUSENTO;LUNGOCALORE;LUNGODORA;LUNGOFIUME;LUNGOFOGLIA;LUNGOLAGO;LUNGOLARIO;LUNGOLIRI;LUNGOMARE;LUNGONERA;LUNGOPARCO;LUNGOPO;LUNGOPORTO;LUNGOSABATO;LUNGOSILE;LUNGOSTURA;LUNGOTEVERE;MASO;MASSERIA;MERCATO;MERCERIA;MOLO;MONTE;MULATTIERA;MULINO;MURA;MURO;NUCLEO;ORTO;PALUDO;PARALLELA;PARCO;PASSAGGIO;PASSAGGIO PEDONALE;PASSAGGIO PRIVATO;PASSEGGIATA;PASSEGGIO;PASSETTO;PASSO;PASSO CHIUSO;PASSO DI PIAZZA;PENDICE;PENDINO;PENDIO;PENNINATA;PIAGGIA;PIANO;PIASA;PIAZZA;PIAZZA I;PIAZZA INFERIORE;PIAZZALE;PIAZZETTA;PIAZZETTA PRIVATA;PIAZZOLA;PIAZZOLO;PINETA;PISCINA;PLESSO;PODERE;POGGIO;PONTE;PONTILE;PORTA;PORTELLA;PORTICCIOLO;PORTICHETTI;PORTICI;PORTICO;PORTO;PRATO;PROLUNGAMENTO;PROVINCIALE;QUADRATO;QUARTIERE;RACCORDO;RACCORDO AUTOSTRADALE;RAMI;RAMO;RAMO V;RAMPA;RAMPARI;RAMPE;RATTO;RECINTO;REDOLA;REGIONE;RESIDENCE;RIELO;RIFUGIO;RIGASTE;RIO;RIO TERRA;RIONE;RIPA;RIVA;RIVIERA;RONDO;ROTONDA;ROUTE;RUA;RUE;RUELLE;RUGA;RUGA GIUFFA;RUGHETA;SACCA;SALITA;SALITA INFERIORE;SALITA NUOVA;SALITA SUPERIORE;SALITA VECCHIA;SALIZADA;SBARCATOIO;SCALA;SCALE;SCALEA;SCALETTA;SCALETTE;SCALI;SCALINATA;SCALO;SCALONE;SCESA;SDRUCCIOLO;SECO;SELCIATO;SENTIERO;SESTIERE;SITO;SLARGO;SOBBORGO;SOPPORTICO;SOTTOPASSAGGIO;SOTTOPASSO;SOTTOPORTICO;SOTTOVIA;SPALTO;SPIAGGIA;SPIANATA;SPIAZZO;STATALE;STAZIONE;STRADA;STRADA ALTA;STRADA ANTICA;STRADA ARGINALE;STRADA B;STRADA COMUNALE;STRADA NAZIONALE;STRADA NUOVA;STRADA PANORAMICA;STRADA PRIVATA;STRADA PROVINCIALE;STRADA RURALE;STRADA STATALE;STRADA TRAVERSANTE;STRADA VECCHIA;STRADA VICINALE;STRADALE;STRADELLA;STRADELLO;STRADETTA;STRADONE;STRETTA;STRETTO;STRETTOIA;STRETTOLA;SUPERSTRADA;SUPPORTICO;SVINCOLO;SVOTO;TANGENZIALE;TANGENZIALE NORD;TANGENZIALE SUD;TENUTA;TERRAZZA;TONDO;TORRENTE;TRASVERSALE;TRATTO;TRATTURO;TRAVERSA;TRAVERSA A;TRAVERSA DESTRA;TRAVERSA H;TRAVERSA I LEVANTE;TRAVERSA I PRIVATA;TRAVERSA NUOVA;TRAVERSA PRIVATA;TRAVERSA VI DESTRA;TRAVERSA X DESTRA;TRESANDA;TRONCO;VALLE;VALLONE;VANELLA;VANELLA III;VARCO;VARIANTE;VIA;VIA ANTICA;VIA BASSA;VIA CHIUSA;VIA COMUNALE;VIA CONSOLARE;VIA DESTRA;VIA E ARCO;VIA ESTERNA;VIA FONDA;VIA I LATERALE SINISTRA;VIA INFERIORE;VIA INTERMEDIA;VIA INTERNA;VIA LATERALE;VIA LITORANEA;VIA MINORE;VIA NAZIONALE;VIA NUOVA;VIA PANORAMICA;VIA PICCOLA;VIA PRIVATA;VIA PROVINCIALE;VIA ROTABILE;VIA SCALINATA;VIA SINISTRA;VIA SOTTO;VIA STATALE;VIA SUPERIORE;VIA TRAVERSA;VIA VECCHIA;VIA VECCHIA COMUNALE;VIA VECCHIA PROVINCIALE;VIA VICINALE;VIADOTTO;VIALE;VIALE II;VIALE PRIVATO;VIALETTO;VICINALE;VICO;VICO A SINISTRA;VICO CHIUSO;VICO CIECO;VICO INFERIORE;VICO INTERNO;VICO INTERNO I;VICO LUNGO;VICO PRIVATO;VICO STORTO;VICO STRETTO;VICO SUPERIORE;VICOLETTO;VICOLETTO CIECO;VICOLO;VICOLO C;VICOLO CHIUSO;VICOLO CIECO;VICOLO MOZZO;VICOLO PRIVATO;VICOLO STORTO;VIELLA;VILLA;VILLAGGIO;VILLINO;VIOTTOLA;VIOTTOLO;VIOTTOLONE;VIUZZO;VO;VOCABOLO;VOLTA;VOLTO;VOLTONE;ZONA;ZONA INDUSTRIALE";
		String[] arrlistvalue=listvalue.split(";");
		int first=options.size();
		if(first==arrlistvalue.length)
		{
			for(int i=0;i<arrlistvalue.length;i++)
			{				
				if (options.get(i).getText().equalsIgnoreCase(arrlistvalue[i])){
					if(i == (first-1)){	
						Reporter.log("All the "+arrlistvalue.length+" types of streets exists in street type list box ");
					}

				}else{
					System.out.println("actual text "+options.get(i).getText()+"expected test "+arrlistvalue[i]);
					System.out.println("all the street type details are not matching");
					Reporter.log(arrlistvalue[i]+ "value not exists in street type List box");
				}
			}
		}
	}




	/* ******************************************************************************/
	/* Method Name : --- ----- --- VerifyListValueStreetcolor
	 * Purpose 	   : --- ----- ---  verify the text present
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS*/

	public void VerifyListValueStreetcolor(){

		Select listbox=new Select(driver.findElement(By.cssSelector("#StreetColor")));
		List<WebElement> options=listbox.getOptions();

		String listvalue="Colore;N;Nero;R;Rosso";
		String[] arrlistvalue=listvalue.split(";");
		int first=options.size();
		{
			for(int i=0;i<arrlistvalue.length;i++)
			{
				if (options.get(i).getText().equalsIgnoreCase(arrlistvalue[i])){

					if(i == (first-1)){	
						Reporter.log("All the "+arrlistvalue.length+" street colors exists in street color list box ");
					}
				}else{
					System.out.println("all the color details not matched");
					Reporter.log(arrlistvalue[i]+ "value not exists in street color List box");
				}
			}
		}
	}


	/* ******************************************************************************/
	/* Method Name : --- ----- --- VerifyListValueProvince
	 * Purpose 	   : --- ----- ---  verify the text present
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS*/

	public void VerifyListValueProvince(){

		Select listbox=new Select(driver.findElement(By.cssSelector("#Province")));
		List<WebElement> options=listbox.getOptions();

		String listvalue="scegli;Agrigento;Alessandria;Ancona;Aosta;Arezzo;Ascoli Piceno;Asti;Avellino;Bari;Barletta-Andria-Trani;Belluno;Benevento;Bergamo;Biella;Bologna;Bolzano;Brescia;Brindisi;Cagliari;Caltanissetta;Campobasso;Carbonia-Iglesias;Caserta;Catania;Catanzaro;Chieti;Como;Cosenza;Cremona;Crotone;Cuneo;Enna;Fermo;Ferrara;Firenze;Foggia;Forli Cesena;Frosinone;Genova;Gorizia;Grosseto;Imperia;Isernia;L'Aquila;La Spezia;Latina;Lecce;Lecco;Livorno;Lodi;Lucca;Macerata;Mantova;Massa;Matera;Medio Campidano;Messina;Milano;Modena;Monza E Della Brianza;Napoli;Novara;Nuoro;Ogliastra;Olbia-Tempio;Oristano;Padova;Palermo;Parma;Pavia;Perugia;Pesaro E Urbino;Pescara;Piacenza;Pisa;Pistoia;Pordenone;Potenza;Prato;Ragusa;Ravenna;Reggio Calabria;Reggio Emilia;Rieti;Rimini;Roma;Rovigo;Salerno;Sassari;Savona;Siena;Siracusa;Sondrio;Taranto;Teramo;Terni;Torino;Trapani;Trento;Treviso;Trieste;Udine;Varese;Venezia;Verbano Cusio Ossola;Vercelli;Verona;Vibo Valentia;Vicenza;Viterbo";
		String[] arrlistvalue=listvalue.split(";");
		int first=options.size();
		{
			for(int i=0;i<arrlistvalue.length;i++)
			{

				if (options.get(i).getText().equalsIgnoreCase(arrlistvalue[i])){

					if(i == (first-1)){	
						Reporter.log("All the "+arrlistvalue.length+" provinces exists in province list box ");
					}
				}else{
					System.out.println("all the province details not matched");
					Reporter.log(arrlistvalue[i]+ "value not exists in Province List box");
				}
			}
		}
	}


	/* ******************************************************************************/
	/* Method Name : --- ----- --- VerifyListValuepresentprefex
	 * Purpose 	   : --- ----- ---  verify the text present
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS*/

	public void VerifyListValuepresentprefex(){

		Select listbox=new Select(driver.findElement(By.cssSelector("#Prefix")));
		List<WebElement> options=listbox.getOptions();

		String listvalue="Titolo;Architetto;Avvocato;Dottore;Geometra;Ingegnere;Ragioniere;Sig;Sig.ra";
		String[] arrlistvalue=listvalue.split(";");
		int first=options.size();
		if(first==arrlistvalue.length)
		{
			for(int i=0;i<arrlistvalue.length;i++)
			{
				if (options.get(i).getText().equalsIgnoreCase(arrlistvalue[i])){
					if(i == arrlistvalue.length-1){
						Reporter.log("All the "+arrlistvalue.length+" prefex exists in prefix List box ");
					}
				}else{
					System.out.println("all the details not matched");        
					Reporter.log(arrlistvalue[i]+ "value not exists in prefix List box");
				}
			}
		}
	}




	/* ******************************************************************************/

	/* Method Name : --- ----- --- waitForPageToLoad
	 * Purpose 	   : --- ----- ---  implicit wait
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public void waitForPageToLoad(int a)
	{
		driver.manage().timeouts().implicitlyWait(a,TimeUnit.SECONDS);

	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- getRowData
	 * Purpose 	   : --- ----- ---  get the row value from the table
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public String[] getRowData(String rowXpath)
	{
		String rowData[]=null;
		try{
			String entireRowData=driver.findElement(By.xpath(rowXpath)).getText();
			rowData=entireRowData.split("");
		}catch(Throwable e){
			System.out.println("Row data not found");
		}
		return rowData;
	}


	/* ******************************************************************************/	

	/* Method Name : --- ----- --- clickAndGetRowData
	 * Purpose 	   : --- ----- --- click on the table and get ther row data
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public String[] clickAndGetRowData(String rowXpath,String clickableElement) throws InterruptedException
	{
		String rowData[]=null;
		while(true){
			try{
				String entireRowData=driver.findElement(By.xpath(rowXpath)).getText();
				rowData=entireRowData.split("");
				break;
			}catch(Throwable e){
				driver.findElement(By.xpath(clickableElement)).click();
				Thread.sleep(3000);
			}
		}
		return rowData;	
	}


	/* ******************************************************************************/	



	/* Method Name : --- ----- --- waitforElementVisible
	 * Purpose 	   : --- ----- --- perform wait till element visible
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public void waitforElementVisible(By by){
		WebDriverWait wait=new WebDriverWait(driver,30);
		//wait.until(ExpectedConditions.presenceOfElementLocated(by));
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}



	/* ******************************************************************************/	

	/* Method Name : --- ----- --- waitFortheText
	 * Purpose 	   : --- ----- ---  perform wait till the text present in the element
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void waitFortheText(By by,String text){
		WebDriverWait wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.textToBePresentInElementValue(by, text));

	}


	/* ******************************************************************************/	

	/* Method Name : --- ----- --- waitForPage
	 * Purpose 	   : --- ----- ---  perform wait for the page title to present
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void waitForPage(String page){
		WebDriverWait wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.titleContains(page));
		Reporter.log("Current Page "+page);
	}


	/* ******************************************************************************/	

	/* Method Name : --- ----- --- waitForElement
	 * Purpose 	   : --- ----- ---  perform wait till the element to be clickable
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public void waitForElement(By by)
	{
		WebDriverWait wait=new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(by));
	}



	/* ******************************************************************************/	

	/* Method Name : --- ----- --- waitForTextPresence
	 * Purpose 	   : --- ----- ---  perform wait till the text present in the element
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public void waitForTextPresence(By by,String text){
		WebDriverWait wait=new WebDriverWait(driver,10);
		wait.until(ExpectedConditions.textToBePresentInElementValue(by, text));
	}


	/* ******************************************************************************/	


	/* Method Name : --- ----- --- HashMap
	 * Purpose 	   : --- ----- ---  click on the child element using child text
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public HashMap<String, String> getWindowID()
	{
		Set<String> set=driver.getWindowHandles();
		HashMap<String, String> windowID=new HashMap<String, String>();
		Iterator<String> it=set.iterator();
		windowID.put("parentID",it.next());
		windowID.put("childID", it.next());
		return windowID;
	}


	/* ******************************************************************************/	


	/* Method Name : --- ----- --- acceptAlert
	 * Purpose 	   : --- ----- --- click on the child element using child text
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public void acceptAlert()
	{
		Alert alt=driver.switchTo().alert();
		alt.accept();

	}


	/* ******************************************************************************/	

	/* Method Name : --- ----- --- dismisstAlert
	 * Purpose 	   : --- ----- ---  click on the child element using child text
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public void dismisstAlert()
	{
		Alert alt=driver.switchTo().alert();
		alt.dismiss();
	}



	/* ******************************************************************************/	


	/* Method Name : --- ----- --- getValues
	 * Purpose 	   : --- ----- --- get the value in an array
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public double[] getValues(By by,String reg){
		double[] Array;
		String eletext;
		waitForElement(by);
		driver.findElements(by);
		List<WebElement> eles=driver.findElements((by));
		Array=new double[eles.size()];
		for (int i=0;i<eles.size();i++){
			eletext=eles.get(i).getText();
			String Arr=eletext.replaceAll(reg, "");
			Array[i]=Double.parseDouble(Arr);
		}
		return Array;
	}


	/* ******************************************************************************/	

	/* Method Name : --- ----- --- clickSpecificElement
	 * Purpose 	   : --- ----- --- click on the speicfic element using  Data 
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */


	public void clickSpecificElement(By by,String Data){
		//waitForElement(by);
		//driver.findElements(by);
		List<WebElement> eles=driver.findElements((by));
		System.out.println(eles.size());
		for (int i=0;i<eles.size();i++){
			System.out.println(eles.get(i).getText());
			if (eles.get(i).getText().equalsIgnoreCase(Data)){
				eles.get(i).click();
				Reporter.log("Click on the super cat link "+by);
				break;
			}
		}
	}



	/* ******************************************************************************/	
	
    /* Method Name : --- ----- --- clickSpecificElement
    * Purpose       : --- ----- --- click on the speicfic element using  Data 
     * Date Created      : --- ----- --- 
     * Created By        : --- ----- --- TCS
    */

    public void clickSpecificElement(By by,int i){
           waitForElement(by);
           List<WebElement> eles=listofelements((by));
           System.out.println(eles.size());
                        eles.get(i-1).click();
                        Reporter.log("Click on the super cat link "+by);
                        waitForPageToLoad(30);

    }




	/* ******************************************************************************/	

	/* Method Name : --- ----- --- clickSpecificElementBy Property
	 * Purpose 	   : --- ----- --- click on the speicfic element using  Property 
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void clickSpecificElementByProperty(By by,String prop,String Data){
		//waitForElement(by);
		driver.findElements(by);
		List<WebElement> eles=driver.findElements((by));
		System.out.println(eles.size());
		for (int i=0;i<eles.size();i++){
			String propvalue=eles.get(i).getAttribute(prop);
			//System.out.println(eles.get(i).getAttribute(prop));
			if (propvalue.equalsIgnoreCase(Data)){
				//if (propvalue.contains(Data)){
				eles.get(i).click();
				Reporter.log("Click on the super cat link "+by);
				waitForPageToLoad(30);
				break;
			}
		}
	}



	/* ******************************************************************************/	

	/* Method Name : --- ----- --- clickOnSpecificElementContains
	 * Purpose 	   : --- ----- --- click on the speicfic element using  Data
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void clickSpecificElementContains(By by,String Data){
		driver.findElements(by);
		List<WebElement> eles=driver.findElements((by));
		System.out.println(eles.size());
		System.out.println(Data);
		for (int i=0;i<eles.size();i++){
			System.out.println(eles.get(i).getText());
			if (eles.get(i).getText().contains(Data)){
				eles.get(i).click();
				break;
			}
		}
	}


	/* ******************************************************************************/	

	/* Method Name : --- ----- --- VerifyOnSpecificElementContains
	 * Purpose 	   : --- ----- --- Verify on the speicfic element using  Data
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public boolean verifySpecificElementContains(By by,String Data){
		boolean flag=false;
		waitforElementVisible(by);
		waitForElement(by);
		driver.findElements(by);
		List<WebElement> eles=driver.findElements((by));
		System.out.println(eles.size());
		for (int i=0;i<eles.size();i++){
			System.out.println(eles.get(i).getText());
			System.out.println(Data);
			if (eles.get(i).getText().contains(Data)){
				Reporter.log("verified the Element text "+by);
				flag=true;
				break;
			}
		}
		return flag;

	}



	/* ******************************************************************************/	

	/* Method Name : --- ----- --- VerifyOnSpecificElement
	 * Purpose 	   : --- ----- --- Verify on the speicfic element using  Data
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public boolean verifySpecificElement(By by,String Data){
		boolean flag=false;
		waitforElementVisible(by);
		waitForElement(by);
		driver.findElements(by);
		List<WebElement> eles=driver.findElements((by));
		System.out.println(eles.size());
		for (int i=0;i<eles.size();i++){
			System.out.println(eles.get(i).getText());
			if (eles.get(i).getText().contains(Data)){
				flag=true;
				break;
			}
		}
		return flag;

	}



	/* ******************************************************************************/

	/* Method Name : --- ----- --- getChildElements
	 * Purpose 	   : --- ----- --- get the child element
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public List<WebElement> getChildElements(By by){
		List<WebElement> eles=driver.findElements(by);
		return eles;
	}


	/* ******************************************************************************/	

	/* Method Name : --- ----- --- clickonChildElement
	 * Purpose 	   : --- ----- --- click on the child element using child text
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void clickChildElement(By parent,By child,String childText){
		waitForElement(parent);
		waitForElement(child);
		System.out.println(childText);
		List<WebElement> ParentObj=driver.findElements(parent);
		List<WebElement> ChildObj=driver.findElements(child);
		System.out.println(ParentObj.size());
		System.out.println(ChildObj.size());
		for (int i=0;i<ParentObj.size();i++){
			System.out.println("Parent Object text :"+ParentObj.get(i).getText());
			System.out.println("Child Object text :"+ChildObj.get(i).getText());
			if (ParentObj.get(i).getText().contains(childText)){
				ChildObj.get(i).click();
				Reporter.log("clicked on the element "+child);
				break;
			}
		}
	}


	/* ******************************************************************************/	

	/* Method Name : --- ----- --- sendKeysChildElement
	 * Purpose 	   : --- ----- --- SendKeys child element using child text
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public void sendKeysChildElement(By parent,By child,String childText,String Text){
		waitForElement(parent);
		waitForElement(child);
		System.out.println(childText);
		List<WebElement> ParentObj=driver.findElements(parent);
		List<WebElement> ChildObj=driver.findElements(child);
		System.out.println(ParentObj.size());
		System.out.println(ChildObj.size());
		for (int i=0;i<ParentObj.size();i++){
			System.out.println(ParentObj.get(i).getText());
			if (ParentObj.get(i).getText().contains(childText)){
				ChildObj.get(i).clear();
				ChildObj.get(i).sendKeys(Text);
				break;
			}
		}
	}



	/* ******************************************************************************/

	/* Method Name : --- ----- --- getRegExpValue
	 * Purpose 	   : --- ----- --- Get the Regexp match value from the given String
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public String getRegExpValue(String str,String regexp){
		String regexpValue="";
		Pattern P=Pattern.compile(regexp);
		Matcher M=P.matcher(str);
		if (M.find()){
			regexpValue=M.group();
		}
		return regexpValue;
	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- verifyPrice
	 * Purpose 	   : --- ----- --- verify the price value
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void verifyPrice(By by,String Price){
		if (isElementPresent(by)) {
			if (getRegExpValue(getTextandreturn(by),"\\d+\\W\\d+").equalsIgnoreCase(Price)) {
				Reporter.log("Price Verified Successfully");
			}else{
				Reporter.log("Price is not as expected and actual is "+getTextandreturn(by));
				//org.testng.Assert.fail("Price is not as expected and actual is "+getTextandreturn(by));
			}
		}else{
			Reporter.log("Object "+by+" is not present ");
		}
	}



	/* ******************************************************************************/

	/* Method Name : --- ----- --- verifyObject
	 * Purpose 	   : --- ----- --- verify the object
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public boolean verifyObject(By by){

		try{
			driver.findElement(by).isDisplayed();
			Reporter.log("PASS_MESSAGE:- The Object with Element "+by+" is displayed");

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- The Web Element "+by+" is not found");
			throw new NoSuchElementException("The element with"+ by.toString().replace("By.", " ")+ " not found");
		}
		return  driver.findElement(by).isDisplayed();
	}




	/* ******************************************************************************/	

	/* Method Name : --- ----- ---   getRoProperty
	 * Purpose 	   	: --- ----- --- get the runtime property
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public String getRoProperty(WebElement ele,String Prop){
		return ele.getAttribute(Prop);
	}


	/* ******************************************************************************/	

	/* Method Name : --- ----- --- editSubmit
	 * Purpose 	   	: --- ----- --- submit the value
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void editSubmit(By by){
		driver.findElement(by).submit();

	}


	/* ******************************************************************************/	

	/* Method Name : --- ----- --- verifypage
	 * Purpose 	   	: --- ----- --- verify the page title
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public boolean verifyPage(String title){
		boolean bflag=false;
		String acttitle=driver.getTitle();
		if (acttitle.contains(title)){
			Reporter.log("Page Verified "+acttitle);
			bflag = true;
		}
		return bflag;
	}



	/* ******************************************************************************/	

	/* Method Name : --- ----- --- dsorting
	 * Purpose 	   	: --- ----- --- sorting arrays in asecding order
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public double[] asorting(double[] array) {
		double temp;
		for (int i = 0; i < array.length; i++) {
			for (int j=i+1;j <array.length;j++) {
				if (array[i] > array[j]) {
					if (j==array.length) {
						break;
					}
					temp=array[j];
					array[j]=array[i];
					array[i]=temp;
				}
			}
		}
		return array;
	}



	/* ******************************************************************************/	

	/* Method Name : --- ----- --- dsorting
	 * Purpose 	   	: --- ----- --- sorting arrays in asecding order
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public double[] dsorting(double[] array) {
		double temp;
		for (int i = 0; i < array.length; i++) {
			for (int j=i+1;j <array.length;j++) {
				if (array[i] < array[j]) {
					if (j==array.length) {
						break;
					}
					temp=array[j];
					array[j]=array[i];
					array[i]=temp;
				}
			}
		}
		return array;
	}



	/* ******************************************************************************/	

	/* Method Name : --- ----- --- compareArrays
	 * Purpose 	   	: --- ----- --- comparing two arrays
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public boolean compareArrays(int[] A1,int[] A2){
		boolean bArrcompare=Arrays.equals(A1, A2);
		if (bArrcompare){
			Reporter.log("Arrays are equal");
		}else{
			Reporter.log("Arrays are not equal");
		}
		return bArrcompare;
	}



	/* ******************************************************************************/	

	/* Method Name : --- ----- --- compareArrays
	 * Purpose 	   	: --- ----- --- comparing two arrays
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public boolean compareArrays(double[] A1, double[] A2) {
		boolean bArrcompare=Arrays.equals(A1, A2);
		if (bArrcompare){
			Reporter.log("Arrays are equal");
		}else{
			Reporter.log("Arrays are not equal");
		}
		return bArrcompare;

	}


	/* ******************************************************************************/	

	/* Method Name : --- ----- --- highlightElement
	 * Purpose 	   	: --- ----- --- Highlight the webelement
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public static void highlight(WebElement element) 
	{
		for(int i=0;i<2;i++)
		{
			((JavascriptExecutor)driver).executeScript("arguments[0].style.backgroundcolor='yellow'",element);
			((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid red'",element);
		}
	}


	/* ******************************************************************************/	
	/* Method Name : --- ----- --- verifyListOption
	 * Purpose 	   	: --- ----- --- verify the list value
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	
	 */

	public void verifyListOption(By by,String text){
		waitForElement(by);
		WebElement select=driver.findElement(by);
		waitForPageToLoad(15);
		List<WebElement> options=select.findElements(By.tagName("option"));

		for (WebElement option:options){
			System.out.println(option.getText());

			if (option.getText().equalsIgnoreCase(text)){
				Reporter.log("expected list is available the value is "+text);
				break;
			}
		}
	}


	/* ******************************************************************************/	

	/* Method Name : --- ----- --- closeBrowser
	 * Purpose 	   	: --- ----- --- close the browser
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void closeBrowser(){
		driver.close();
	}


	/* ******************************************************************************/	

	/* Method Name : --- ----- --- back
	 * Purpose 	   	: --- ----- --- click on the back key
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void back(){
		driver.navigate().back();
	}


	/* ******************************************************************************/	

	/* Method Name : --- ----- --- verifyLink
	 * Purpose 	   	: --- ----- --- verify the link
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void verifyLink(String text){
		List<WebElement> Links=driver.findElements(By.tagName("a"));

		for (WebElement Link:Links){
			System.out.println(Link.getText());

			if (Link.getText().equalsIgnoreCase(text)){
				Reporter.log("expected Link is available the value is "+Link.getText());
				break;
			}
		}
	}


	/* ******************************************************************************/	

	/* Method Name : --- ----- --- getPropertyValue
	 * Purpose 	   	: --- ----- --- ge the property value
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public String getPropertyValue(By by,String property){
		return driver.findElement(by).getAttribute(property);
	}


	/* ******************************************************************************/	

	/* Method Name : --- ----- --- deleteCookies
	 * Purpose 	   	: --- ----- --- delete all cookies
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void deleteCookies(){
		driver.manage().deleteAllCookies();
	}


	/* ******************************************************************************/	

	/* Method Name : --- ----- --- clickObjectByMatchingPropertyValue
	 * Purpose 	   	: --- ----- --- click on the Object by matching the given prop value
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void clickObjectByMatchingPropertyValue(By by,String property,String Data){
		waitforElementVisible(by);
		waitForElement(by);
		List<WebElement> colls=driver.findElements(by);
		for (int i=0;i<colls.size();i++){
			String propValue= colls.get(i).getAttribute(property);
			String text=colls.get(i).getText();
			if (propValue.contains(Data)){
				colls.get(i).click();
				Reporter.log("Clicked on "+text);
				break;
			}
		}
	}


	/* Method Name : --- ----- --- Verify Element Count
	 * Purpose          : --- ----- --- Verify Element coun and verify
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- Bala
	 */

	public void VerifyElementCount(By by,String Data){
		waitforElementVisible(by);
		waitForElement(by);
		driver.findElements(by);
		List<WebElement> eles=driver.findElements((by));
		System.out.println(eles.size());
		int Data1=Integer.parseInt(Data);
		if (eles.size()==Data1)
		{
			Reporter.log("The Count is same as expected and Count is : "+eles.size());
		}
		else
		{
			Reporter.log("The Count is Not same as expected and Count is : "+eles.size());
		}
	}


	/* ******************************************************************************/	

	/* Method Name : --- ----- --- clickAllCollectionElement
	 * Purpose 	   	: --- ----- --- click on the element one by one
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void clickAllCollectionElement(By by){
		waitForElement(by);
		List<WebElement> colls=driver.findElements(by);
		System.out.println(colls.size());
		for (int i=0;i<colls.size();i++){
			colls.get(i).click();	
			Reporter.log("clicked on item no"+i);
		}
	}


	/* ******************************************************************************/	

	/* Method Name : --- ----- --- clickChildLink
	 * Purpose 	   	: --- ----- --- click on link based on the link text
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void clickChildLink(By by,String text){

		try{	
			WebElement ele=driver.findElement(by);
			List<WebElement> lnks= ele.findElements(By.tagName("a"));
			System.out.println("total links are "+lnks.size());
			for (int i=0;i<lnks.size();i++){
				System.out.println(lnks.get(i).getText());
				if (lnks.get(i).getText().contains(text)){
					lnks.get(i).click();
					break;
				}
			}

			Reporter.log("PASS_MESSAGE:- The Child Bread Crumb Link "+text+" with Element "+by+" is clicked");

		}
		catch(NoSuchElementException e){
			Reporter.log("FAIL_MESSAGE:- The Web Element "+by+" is not found");
			throw new NoSuchElementException("The element with"+ by.toString().replace("By.", " ")+ " not found");
		}
	}


	/* ******************************************************************************/	

	/* Method Name : --- ----- --- Webelement
	 * Purpose 	   	: --- ----- --- Return Webelement
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public WebElement element(By by){
		return driver.findElement(by);
	}


	/* *****************************************************************************/

	/* Method Name : --- ----- --- Verifyemptyfields
	 * Purpose     : --- ----- --- Verify the empty text box or fields
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void VerifyEmptyFields(By by)
	{
		if(driver.findElement(by).getAttribute("value").equals("")){
			Reporter.log("The Expected field is empty");
		}
		else{
			Reporter.log("The Expected field is not empty");
		}

	}


	/* *****************************************************************************/

	/* Method Name : --- ----- --- Verifyemptyfields
	 * Purpose     : --- ----- --- Verify the empty text box or fields
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void VerifyEnabledFields(By by)
	{
		if(driver.findElement(by).isEnabled()){
			Reporter.log("The Expected field is Enabled");
		}
		else{
			Reporter.log("The Expected field is not Enabled");
		}
	}


	/* *****************************************************************************/

	/* Method Name : --- ----- --- Verifyspecificelementdisplayed
	 * Purpose     : --- ----- --- Verify the specific element displayed or not
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void VerifyspecificelementDisplayed(By by)
	{
		if(driver.findElement(by).isDisplayed()){
			Reporter.log("The Specific Element is Displayed");
		}
		else{
			Reporter.log("The Specific Element is not Displayed");
		}
	}


	/* *****************************************************************************/ 

	/* public void captureScreenShot(ITestResult result){
				 File file=new File("");
				 File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			        // now save the screenshto to a file some place
			        try {
			        	FileUtils.copyFile(scrFile, new File(file.getAbsolutePath()+"Test-Results_"+InetAddress.getLocalHost().getHostName()+"_"+System.getProperty("user.name")+"_"+currentDateAndTime+"/html/" + result.getName() + ".jpg"));
			            Reporter.log("<a href='"+ file.getAbsolutePath()+"/selenium-reports/html/" + result.getName() + ".jpg'> <img src='"+ file.getAbsolutePath()+"/selenium-reports/html/"+ result.getName() + ".jpg' height='100' width='100'/> </a>");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 }*/



	/* *****************************************************************************/

	/* Method Name : --- ----- --- BrowserQuit
	 * Purpose     : --- ----- --- Quit the Application
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void BrowserQuit() {
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- The Browser should be closed");
		try{
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.quit();
			System.out.println("Browser Closed");
			Reporter.log("PASS_MESSAGE:-The Browser is closed");
		}
		catch(NoSuchElementException e){
			Reporter.log("FAIL_MESSAGE:- No Browser is opened currently");

		}

	}


	/* *****************************************************************************/

	/* Method Name : --- ----- --- Login_Verification
	 * Purpose     : --- ----- --- Verify the User logged in or not
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void Login_Verification(String UserName, String Password ) throws InterruptedException{

		try {
			fluentWait(By.id("txtUserName"));
			sendKeys(By.id("txtUserName"), UserName);
			sendKeys(By.id("Password"), Password);
			click(By.id("RememberMe"));
			Thread.sleep(2000);
			click(By.cssSelector(".formBtn.button"));
			Thread.sleep(20000);
			if ((driver.findElement(By.cssSelector("#welcome>span"))).isDisplayed()) {
				System.out.println("User logged in Successfully");
				Reporter.log("User logged in Successfully");
			}
		}
		catch (Exception e) {
			Reporter.log("User logged in UnSuccessfully");	
		}
	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- Verify_Any_WebElement
	 * Purpose     : --- ----- --- Verify Any WebElement is displayed or not
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void Verify_Any_WebElement(By byExpectedObj){

		try {
			//fluentWait(byExpectedObj);
			waitforElementVisible(byExpectedObj);
			if ((driver.findElement(byExpectedObj)).isDisplayed()){
				System.out.println("Expected WebElement is Displayed");
				Reporter.log("Expected WebElement is Displayed");
			}
		}
		catch (Exception e){
			Reporter.log("Expected WebElement is not Displayed");
			System.out.println("Expected Webelement is not Displayed");
		}
	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- Click_On_Any_Object
	 * Purpose     : --- ----- --- Click Any Object
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void Click_On_Any_Object(By byExpectedObj){

		try {
			waitforElementVisible(byExpectedObj);
			if ((driver.findElement(byExpectedObj)).isDisplayed()){
				String expObj = driver.findElement(byExpectedObj).getText();
				click(byExpectedObj);
				System.out.println(expObj +" Object is Clicked");
				Reporter.log(expObj +" Object is Clicked");
			}
		}			    	
		catch (Exception e) {
			Reporter.log("Expected Object is not Displayed");			
		}
	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- Verify_Page
	 * Purpose     : --- ----- --- Verify any Page
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void Verify_Page(String expData){

		try {
			String actTitle = driver.getTitle();
			if (actTitle.equals(expData)){
				System.out.println("Expected Page is displayed : " + expData);
				Reporter.log("Expected Page is displayed : " + expData );
			}
		}			    	
		catch (Exception e) {			
			Reporter.log("Expected Page is not displayed : " + expData);			
		}
	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- Any_WebEdit_Entry
	 * Purpose     : --- ----- --- We can enter any webedit entry in specific field
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void Any_WebEdit_Entry(By byExpObj, String expVal) throws InterruptedException{

		try {
			waitforElementVisible(byExpObj);
			driver.findElement(byExpObj).sendKeys(expVal);
			Thread.sleep(3000);
			fluentWait(byExpObj);
			//String actVal = driver.findElement(byExpObj).getText();
			String actVal = driver.findElement(byExpObj).getAttribute("value");			
			if (expVal.equals(actVal)){
				System.out.println("Expected value inserted in expected field : " + expVal);
				Reporter.log("Expected value inserted in expected field : " + expVal);
			}
		}			    	
		catch (Exception e) {			
			Reporter.log("Expected value not inserted in expected field");			
		}
	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- Verify_SkuPage
	 * Purpose     : --- ----- --- Verify Sku page is displayed or not
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void Verify_SkuPage(By expSkuObj){

		try {
			waitforElementVisible(expSkuObj);			    	
			if (driver.findElement(expSkuObj).isDisplayed()) {
				System.out.println("Sku Page Displayed");
				Reporter.log("Sku Page Displayed");
			}
		}			    	
		catch (Exception e) {			
			Reporter.log("Sku Page not Displayed");			
		}
	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- Verify_ExpectedItemSkuPage_Displayed
	 * Purpose     : --- ----- --- Verify Expected Item Displayed in Sku Page 
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void Verify_ExpectedItemSkuPage_Displayed(By expSkuDesc, String actVal){

		try {
			waitforElementVisible(expSkuDesc);	
			String expVal = driver.findElement(expSkuDesc).getText();
			if (expVal.contains(actVal)){
				System.out.println(actVal + " Item is displayed in Sku page");
				Reporter.log(actVal + " Item is displayed in Sku page");
			}
		}			    	
		catch (Exception e) {			
			Reporter.log(actVal + " Item is not displayed in Sku page");			
		}

	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- Verify_ExpectedLabels_in_expPage_Displayed
	 * Purpose     : --- ----- --- Verify Expected Labels Displayed in expected Page  
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void Verify_ExpectedLabels_in_expPage_Displayed(By expDesc, String actVal){

		try {
			waitforElementVisible(expDesc);
			String expVal = driver.findElement(expDesc).getText();
			String expPage = driver.getTitle();
			System.out.println(expVal);
			System.out.println(actVal);
			if (expVal.contains(actVal)){
				System.out.println(actVal + " Label / Value is displayed in " + expPage + "Page");
				Reporter.log(actVal + " Label / Value is displayed in " + expPage + "Page");
			}
		}			    	
		catch (Exception e) {			
			Reporter.log(actVal + " Label / Value is not displayed in Expected Page");			
		}
	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- Verify_ExpectedLabels_NotDisplayed_in_WithInTheObject_at_expPage
	 * Purpose     : --- ----- --- Verify Expected Labels Not Displayed in With in the Object at expected Page  
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void Verify_ExpectedLabels_NotDisplayed_in_WithInTheObject_at_expPage(By expDesc, String actVal){

		String expPage = driver.getTitle();
		try {
			fluentWait(expDesc);
			String expVal = driver.findElement(expDesc).getText();				    	
			if (expVal.contains(actVal)){
				Reporter.log(actVal + " Label / Value is displayed in Expected Page");				
			}
		}			    	
		catch (Exception e) {
			System.out.println(actVal + " Label / Value is not displayed in " + expPage + "Page");
			Reporter.log(actVal + " Label / Value is not displayed in " + expPage + "Page");
		}
	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- Select_Any_Element_from_ListElements
	 * Purpose     : --- ----- --- Select Any Element from Any List Elements  
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void Select_Any_Element_from_ListElements(By expObj, int option) {

		try {
			waitforElementVisible(expObj);	
			List<WebElement> Element = driver.findElements(expObj);
			if (option > 0 && option <= Element.size()) {
				Element.get(option - 1).click();
				System.out.println("Expected Element selected from the Expected ListOfElements");
				Reporter.log("Expected Element selected from the Expected ListOfElements");
			}
		}
		catch (Exception e) {			
			System.out.println("Expected Element not selected from the Expected ListOfElements");
			Reporter.log("Expected Element not selected from the Expected ListOfElements");
		}
	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- Select_Any_Value_from_AnyDropdown
	 * Purpose     : --- ----- --- Select Any Value from Any Dropdown  
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void Select_Any_Value_from_AnyDropdown(By byDropdown, String expValue) throws InterruptedException {

		try {
			waitforElementVisible(byDropdown);	
			Select sel = new Select(driver.findElement(byDropdown));
			//sel.deselectAll();
			sel.selectByVisibleText(expValue);
			Thread.sleep(20000);
			//String actVal = driver.findElement(byDropdown).getText();
			//String actVal = driver.findElement(byDropdown).getAttribute("value");
			WebElement Option = sel.getFirstSelectedOption();
			String actVal = Option.getText();
			System.out.println("Expected Val:" + expValue);
			System.out.println("Actual Val:" + actVal);
			//String expValue1 = expValue.toLowerCase();
			//String actVal1 = actVal.toLowerCase();				    

			if (expValue.equals(actVal)){
				System.out.println(expValue +" value Selected from Dropdown");
				Reporter.log(expValue +" value Selected from Dropdown");
			}
		}			    	
		catch (Exception e) {			
			System.out.println(expValue +" value not Selected from Dropdown");
			Reporter.log(expValue +" value not Selected from Dropdown");
		}
	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- Verify_Any_WebElement_Not_Exist
	 * Purpose     : --- ----- --- Verify Any WebElement Not Exist  
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void Verify_Any_WebElement_Not_Exist(By byExpectedObj){

		try {
			if ((driver.findElement(byExpectedObj)).isDisplayed()){
				Reporter.log("Expected WebElement is Displayed");
			}
		}			    	
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Expected WebElement is not Displayed");
			Reporter.log("Expected WebElement is not Displayed");
		}
	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- Verify_Any_Text_Exist
	 * Purpose     : --- ----- --- Verify Any Text Exist or not  
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void Verify_Any_Text_Exist(By by, String expText) {

		try {
			fluentWait(by);	
			String actualText=driver.findElement(by).getText();		
			System.out.println(actualText);
			System.out.println(expText);
			if (actualText.equals(expText)){
				System.out.println(expText + "  is displayed");
				Reporter.log(expText + "  is displayed");
			}
		}			    	
		catch (Exception e) {			
			Reporter.log(expText + "  is not displayed");			
			e.printStackTrace();
		}
	}


	/* ******************************************************************************/			    

	/* Method Name : --- ----- --- verifySpecificElementByProperty
	 * Purpose     : --- ----- --- verify the element value by property
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public void verifySpecificElementByProperty(By by,String prop,String option){

		waitForElement(by);                   
		List<WebElement> eles=driver.findElements((by));
		System.out.println(eles.size());
		for (int i=0;i<eles.size();i++){
			if (getRoProperty(eles.get(i),prop) != null){
				String propvalue=eles.get(i).getAttribute(prop);
				System.out.println("PropValue is :"+propvalue);
				System.out.println("expected value "+eles.get(i).getAttribute("value"));
				if (eles.get(i).getAttribute("value").equalsIgnoreCase(option)){
					Reporter.log("value is  "+eles.get(i).getAttribute("value"));
					waitForPageToLoad(30);
					break;
				}
			}
		}
	}



	/* ******************************************************************************/	

	/* Method Name : --- ----- --- sendKeysByMatchingPropertyValue
	 * Purpose     : --- ----- --- enter the text in the specific object by p
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void sendKeysByMatchingPropertyValue(By by,String property,String Data,String Text){
		List<WebElement> colls=driver.findElements(by);
		for (int i=0;i<colls.size();i++){
			String propValue= colls.get(i).getAttribute(property);
			String text=colls.get(i).getText();
			if (propValue.contains(Data)){
				colls.get(i).clear();
				colls.get(i).sendKeys(Text);
				Reporter.log("Clicked on "+text);
				break;
			}
		}
	}


	/* ******************************************************************************/	

	/* Method Name : --- ----- --- verifyByProperty
	 * Purpose     : --- ----- --- verify by property for image
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void verifyByProperty(By by,String prop,String expText){
		waitForElement(by);
		String ImgValue=driver.findElement(by).getAttribute(prop);
		if (ImgValue.equalsIgnoreCase(expText)){
			Reporter.log("Exp text "+expText+" is displayed");
		}else{
			Reporter.log("Exp Text "+expText+" is not displayed");
		}
	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- isnotExist
	 * Purpose     : --- ----- --- verify the object not exist
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public boolean isnotExist(By by){
		boolean flag=false;
		try{
			if (driver.findElement(by).isDisplayed()){
				flag= false;   
			}

		}catch (Exception e){
			flag= true;
		}
		return flag;
	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- getChildElementText
	 * Purpose    : --- ----- --- get the element text based on parent object value
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public String getChildElementText(By parent,By child,String childText){
		String text="";
		waitForElement(parent);
		waitForElement(child);
		System.out.println(childText);
		List<WebElement> ParentObj=driver.findElements(parent);
		List<WebElement> ChildObj=driver.findElements(child);
		System.out.println(ParentObj.size());
		System.out.println(ChildObj.size());
		for (int i=0;i<ParentObj.size();i++){
			System.out.println(ParentObj.get(i).getText());
			if (ParentObj.get(i).getText().contains(childText)){
				System.out.println("Child text "+ChildObj.get(i).getAttribute("value"));
				text=ChildObj.get(i).getAttribute("value");
				Reporter.log("clicked on the element "+child);
				break;
			}
		}
		return text;
	}


	/* ******************************************************************************/

	/* Method Name : --- ----- - clickLinkByText
	 * Purpose          : --- ----- --- click the link by its text
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	
	 */       

	public void clickLinkByText(String text){
		waitForPageToLoad(300);
		List<WebElement> lnks=driver.findElements(By.tagName("a"));
		for (int i=0;i<lnks.size();i++){
			System.out.println(lnks.get(i).getText());
			if (lnks.get(i).getText().contains(text)){
				lnks.get(i).click();
				break;
			}
		}
	}


	/* ******************************************************************************/
	/* Method Name : --- ----- - verifyString
	 * Purpose          : --- ----- --- verify the string
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */       

	public boolean verifyString(String act,String exp){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- The Expected String "+exp+" should be match with Actual String "+act);
		boolean flag = false; 
		if (act.equalsIgnoreCase(exp)){
			System.out.println("Actual is equal to expected and value is "+act);
			Reporter.log("PASS_MESSAGE:- The Expected String "+exp+" is a match with Actual String "+act);            
			flag = true;
		}else{
			System.out.println("Actual is not equal to expected and value is "+act);
			Reporter.log("FAIL_MESSAGE:- The Expected String "+exp+" is not a match with Actual String "+act);
		}
		return flag;
	}


	/* ******************************************************************************/

	/* Method Name : --- ----- - getChildLink
	 * Purpose          : --- ----- --- Get Child Link
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public String[] getChildLink(By by){
		String[] lnk;
		WebElement ele=driver.findElement(by);
		List<WebElement> lnks= ele.findElements(By.tagName("a"));
		System.out.println("total links are "+lnks.size());
		lnk=new String[lnks.size()];
		for (int i=0;i<lnks.size();i++){
			lnk[i]=lnks.get(i).getText();
		}
		return lnk;
	}




	/* ******************************************************************************/

	/* Method Name : --- ----- - getSpecificElementContains
	 * Purpose          : --- ----- ---  get Specific element contains
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public String getSpecificElementContains(By by,String Data){
		String text="";
		waitforElementVisible(by);
		waitForElement(by);
		driver.findElements(by);
		List<WebElement> eles=driver.findElements((by));
		System.out.println(eles.size());
		for (int i=0;i<eles.size();i++){
			if (eles.get(i).getText().contains(Data)){
				System.out.println("Actual text is :"+eles.get(i).getText());
				text=eles.get(i).getText();
				break;
			}
		}
		return text;
	}


	/* ******************************************************************************/

	/* Method Name : --- ----- - ReportElementsText
	 * Purpose          : --- ----- --- ReportElementsText
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public  void ReportElementsText(By by){
		StringBuffer breadcrum = new StringBuffer();
		try{
			waitforElementVisible(by);
			waitForElement(by);
			driver.findElements(by);
			List<WebElement> eles=driver.findElements((by));
			System.out.println(eles.size());
			for (int i=0;i<eles.size();i++){
				breadcrum.append(eles.get(i).getText());
				breadcrum.append(" , ");
				System.out.println(eles.get(i).getText());
			}

			Reporter.log("PASS_MESSAGE:- The Element "+by+" should be present with all the test as "+breadcrum);
		}
		catch(NoSuchElementException e){
			Reporter.log("FAIL_MESSAGE:- The Web Element "+by+" is not found"); 
			throw new NoSuchElementException("The element with"
					+ by.toString().replace("By.", " ")
					+ " not found");

		}
	}




	/* ******************************************************************************/

	/* Method Name : --- ----- - verifymandatoryfields
	 * Purpose          : --- ----- --- verify mandatoryfields
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void verifymandatoryfields(String tag,String classname){

		List<WebElement> eles=driver.findElements(By.tagName(tag));
		for(WebElement e:eles){
			String else1=e.getAttribute("class");
			if(else1.equalsIgnoreCase(classname))
			{
				System.out.println(e.getAttribute("id"));
				Reporter.log("All mandatory fields has been verified");
			}

		}
	}



	/* ******************************************************************************/

	/* Method Name  : --- ----- --- Verifyingerrormessage
	 * Purpose     : --- ----- --- Verify all errror message displayed for the mandatory fields
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void Verifyingerrormessage(String exceldata, By by ){

		waitforElementVisible(by);
		String validationmessage = driver.findElement(by).getText();
		if(exceldata.equals(validationmessage)){

			System.out.println(validationmessage);
			Reporter.log("'-- ("+exceldata+") --' Error message is correct");  
		}
		else if(validationmessage.equals(""))
		{
			System.out.println("no error message is displayed");
		}else{
			System.out.println(validationmessage+" is not matched with -- "+exceldata);
		}
	}




	/* ******************************************************************************/

	/* Method Name  : --- ----- --- Verifyingerrormessage
	 * Purpose     : --- ----- --- Verify all errror message displayed for the mandatory fields
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void VerifyingerrormessageContains(String exceldata, By by ){

		waitforElementVisible(by);
		String validationmessage = driver.findElement(by).getText();		
		if(validationmessage.contains(exceldata)){
			System.out.println(validationmessage);
			Reporter.log("Error message is correct");  
		}
		else if(validationmessage.equals("")){
			System.out.println("no error message is displayed");
		}else{
			System.out.println(validationmessage+" is not matched with -- "+exceldata);
		}
	}



	/* ******************************************************************************/

	/* Method Name  : --- ----- --- getUrl
	 * Purpose     : --- ----- --- get Current Url
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public String getUrl(){
		return driver.getCurrentUrl();
	} 



	/* ******************************************************************************/

	/* Method Name  : --- ----- --- verifyElementProperty
	 * Purpose     : --- ----- --- verify Element Property
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public boolean verifyElementProperty(By by,String prop,String value){
		flag=false;
		WebElement ele=driver.findElement(by);
		if (ele.getAttribute(prop).equalsIgnoreCase(value)){
			flag=true;
		}else{
			flag=false;
		}
		return flag;
	}



	/* ******************************************************************************/

	/* Method Name : --- ----- --- VerifyListValuecountState
	 * Purpose          : --- ----- --- Verify the list value count from the dropdown list
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void VerifyListValuecountstate(){

		Select listbox=new Select(driver.findElement(By.cssSelector("#State")));
		List<WebElement> options=listbox.getOptions();

		for (WebElement option:options){
			int count=options.size();
			if(count==64)
				Reporter.log(option.getText());
			else{
				Reporter.log("corrssponding list value not available");
				break;
			}
		}
	}


	/* ******************************************************************************/
	/* Method Name : --- ----- --- VerifyListValuepresent
	 * Purpose          : --- ----- --- Verify the list value present from the dropdown list
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void VerifyListValuepresentcompany(){

		Select listbox=new Select(driver.findElement(By.cssSelector("#BusinessFoundation")));
		List<WebElement> options=listbox.getOptions();

		String listvalue="--Per favore seleziona l 'anno--;2005 o prima;2006;2007;2008;2009;2010;2011;2012;2013;2014;2015;non lo so";
		String[] arrlistvalue=listvalue.split(";");
		int first=options.size();
		if(first==arrlistvalue.length)
		{
			for(int i=0;i<arrlistvalue.length;i++)
			{				
				if (options.get(i).getText().equalsIgnoreCase(arrlistvalue[i])){
					if(i == (first-1)){	
						Reporter.log("All the "+arrlistvalue.length+" years exists in presentcompany list box ");
					}

				}else{
					System.out.println("all the compnay years are present");

					Reporter.log(arrlistvalue[i]+ "value not exists in company establishment List box");
				}
			}
		}
	}



	/* ******************************************************************************/
	/* Method Name : --- ----- --- select current year
	 * Purpose          : --- ----- --- selecte the current year
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public void selectcurrentyear()
	{
		Select listbox=new Select(driver.findElement(By.cssSelector("#BusinessFoundation")));
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String curryear=String.valueOf(year);		
		listbox.selectByValue(curryear);

	}


	/* Method Name : --- ----- --- selectPreviousyear
	 * Purpose          : --- ----- --- selecte the previous year
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void selectPreviousyear()
	{
		Select listbox=new Select(driver.findElement(By.cssSelector("#BusinessFoundation")));

		int year = Calendar.getInstance().get(Calendar.YEAR);
		int previousyear=year-1;
		System.out.println("current year--"+year+"previous year--"+previousyear);
		String preyear=String.valueOf(previousyear);
		listbox.selectByValue(preyear);

	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- VerifyListValuepresentmonth
	 * Purpose          : --- ----- --- Verify the list value present from the dropdown list
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void VerifyListValuepresentmonth(){

		Select listbox=new Select(driver.findElement(By.cssSelector("#PreviousYearEstablishedMonth")));
		List<WebElement> options=listbox.getOptions();

		System.out.println(options.size()+"month");		
		String listvalue="Seleziona il mese;Gennaio;Febbraio;Marzo;Aprile;Maggio;Giugno;Luglio;Agosto;Settembre;Ottobre;Novembre;Dicembre";
		String[] arrlistvalue=listvalue.split(";");
		int first=options.size();
		if(first==arrlistvalue.length)
		{
			for(int i=0;i<arrlistvalue.length;i++)
			{
				if (options.get(i).getText().equalsIgnoreCase(arrlistvalue[i])){
					Reporter.log(arrlistvalue[i]+ "value exists in month List box ");

				}else{
					System.out.println("all the details not matched");

					Reporter.log(arrlistvalue[i]+ "value not exists in primary office supply provider List box");

				}
			}
		}
	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- VerifyListValuepresentmonth
	 * Purpose          : --- ----- --- Verify the list value present from the dropdown list
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void VerifyListValuepresentmonth(int month){

		Select listbox=new Select(driver.findElement(By.cssSelector("#CurrentYearEstablishedMonth")));
		List<WebElement> options=listbox.getOptions();

		System.out.println(options.size()+"month");		
		String listvalue="Seleziona il mese;Gennaio;Febbraio;Marzo;Aprile;Maggio;Giugno;Luglio;Agosto;Settembre;Ottobre;Novembre;Dicembre";

		String[] arrlistvalue=listvalue.split(";");

		for(int i=1;i<=month+1;i++)
		{			
			if (options.get(i).getText().equalsIgnoreCase(arrlistvalue[i])){
				Reporter.log(arrlistvalue[i]+ "value exists in month List box ");

			}else{
				System.out.println("all the details not matched");
				Reporter.log(arrlistvalue[i]+ "value not exists in primary office supply provider List box");

			}
		}		
	}



	/* ******************************************************************************/

	/* Method Name : --- ----- --- Verifyfieldsmaxlength
	 * Purpose          : --- ----- --- Verify fields max length
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void Verifyfieldsmaxlength(By by,int exelength){
		WebElement ele=driver.findElement(by);
		String actuallength=ele.getAttribute("maxlength");
		if(actuallength.equals(exelength)){

			System.out.println("verified field ");
			Reporter.log("Verified field Actual length is:"+actuallength);

		}
		else{
			Reporter.log("Actual length is not equal to expected length");
		}


	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- Verifyfieldsmaxlength
	 * Purpose          : --- ----- --- Verify fields max length
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void Verifyfieldsmaxlength(String fieldname, By by,int exelength){
		WebElement ele=driver.findElement(by);
		String actuallength=ele.getAttribute("maxlength");
		String exeleg = String.valueOf(exelength);
		if(actuallength.equals(exeleg)){

			System.out.println(fieldname+" max length is exactly "+exeleg);
			Reporter.log("Verified field Actual length is:"+actuallength);
		}
		else{
			Reporter.log("Actual length is not equal to expected length");
		}


	}




	/* ******************************************************************************/

	/* Method Name : --- ----- --- verifySpecificElementContainsReverse
	 * Purpose          : --- ----- --- verify Specific Element Contains Reverse
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public boolean verifySpecificElementContainsReverse(By by,String Data){
		boolean flag=false;
		waitforElementVisible(by);
		waitForElement(by);
		driver.findElements(by);
		List<WebElement> eles=driver.findElements((by));
		System.out.println(eles.size());
		for (int i=0;i<eles.size();i++){
			System.out.println(eles.get(i).getText());
			if (Data.contains(eles.get(i).getText())){
				Reporter.log("verified the Element :  "+eles.get(i).getText());
				System.out.println("verified the Element :    "+eles.get(i).getText());
				flag=true;
				//break;
			}
			else
				System.out.println("Item is not exist "+by);
		}
		return flag;

	}




	/* ******************************************************************************/

	/* Method Name : --- ----- --- verifySpecificElementContainsFromSheetandClick
	 * Purpose          : --- ----- --- verify Specific Element Contains From Sheet and Click
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public boolean verifySpecificElementContainsFromSheetandClick(By by,String Data, By FooterLink){
		boolean flag=false;
		waitforElementVisible(by);
		waitForElement(by);
		driver.findElements(by);
		List<WebElement> eles=driver.findElements((by));
		System.out.println(eles.size());
		for (int i=0;i<eles.size();i++){
			System.out.println(eles.get(i).getText());
			if (Data.contains(eles.get(i).getText())){
				Reporter.log("verified the Element :  "+eles.get(i).getText());
				System.out.println("verified the Element :    "+eles.get(i).getText());
				eles.get(i).click();			

				flag=true;
			}
			else
				System.out.println("Item is not exist "+by);
		}
		return flag;

	}


	/* ******************************************************************************/
	/* Method Name : --- ----- --- Verifyvalidationerror
	 * Purpose          : --- ----- --- Verify Validation error msg for all madatoary fields
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public void verifymandatoryfieldsValidationerrormsg(String tag,String classname){

		List<WebElement> eles=driver.findElements(By.tagName(tag));
		//List<WebElement> eles=driver.findElements(By.className(tag));
		for(WebElement e:eles){
			String else1=e.getAttribute("class");
			System.out.println(e.getText());
			if(else1.equalsIgnoreCase(classname))
			{
				System.out.println(e.getText());
				Reporter.log("Validation error msg has been verified");
			}

		}
	}

	/* **************************************************************************** */          

	/* Method Name : --- ----- --- verifyByPropertypartial
	 * Purpose     : --- ----- --- verify by property for image
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	 */


	public void verifyByPropertypartial(By by,String prop,String expText){
		waitForElement(by);
		String ImgValue=driver.findElement(by).getAttribute(prop);
		if (ImgValue.contains(expText)){
			Reporter.log("Exp text "+expText+" is displayed");
		}else{
			Reporter.log("Exp Text "+expText+" is not displayed");
		}
	}



	/* **************************************************************************** */
	/* Method Name : --- ----- --- verifyUrl
	 * Purpose     : --- ----- --- verify Url
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	 */


	public boolean verifyUrl(String expUrl){
		return verifyString(getUrl(),expUrl);

	}


	/* **************************************************************************** */
	/* Method Name : --- ----- --- verifytxtValue
	 * Purpose     : --- ----- --- verify text Value
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	 */


	public boolean verifytxtValue(By by,String value){
		flag=false;
		WebElement ele=driver.findElement(by);
		if (getRoProperty(ele, "value").equalsIgnoreCase(value)){
			flag=true;
			Reporter.log("Expected text "+value+" is present in the edit box");
		}else{
			flag=false;
			Reporter.log("<font color=\"FFFFFF\" style=\"background-color:#FF0000\">Expected text "+value+" is not present in the edit box </font>");
		}
		return flag;
	}


	/* **************************************************************************** */
	/* Method Name : --- ----- --- verifyparentChecked
	 * Purpose     : --- ----- --- verify Checkbooks is Checked
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	 */


	public boolean verifyCheckboxChecked(By by){
		flag=false;
		if (getPropertyValue(by,"checked").equalsIgnoreCase("checked")){
			flag=true;
			Reporter.log(" Checkbox "+by+" is checked");
		}else{
			flag=false;
			Reporter.log("checkbox "+by+" is not checked");
		}
		return flag;
	}



	/* **************************************************************************** */
	/* Method Name : --- ----- --- getWebElementlabel
	 * Purpose     : --- ----- --- get Web Element label
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	 */
	public String getWebElementlabel(By by){
		WebElement ele=driver.findElement(by);
		return ele.findElement(By.tagName("label")).getText();
	}


	/* **************************************************************************** */
	/* Method Name : --- ----- --- getWebTablink
	 * Purpose     : --- ----- --- get Web Tab Link
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	 */

	public String[] getWebTableLink(By by){
		String[] lnktext = null;
		WebElement table=driver.findElement(by);
		List<WebElement> TableRows= table.findElements(By.tagName("tr"));
		for (WebElement cell:TableRows){
			List<WebElement> rowData=cell.findElements(By.tagName("td"));
			lnktext=new String[rowData.size()];
			for (int i=0;i<rowData.size();i++){
				if (rowData.get(i).findElement(By.tagName("a")).isDisplayed()){
					lnktext[i]=rowData.get(i).findElement(By.tagName("a")).getText();
				}
			}
		}
		return lnktext;
	}


	/* **************************************************************************** */
	/* Method Name : --- ----- --- getWebTablink
	 * Purpose     : --- ----- --- get Web Tab Link
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	 */

	public void WebTableLink(By by,By obj,String linktext){
		WebElement table=driver.findElement(by);
		List<WebElement> TableRows= table.findElements(obj);
		System.out.println("Total rows :"+TableRows.size());
		for (int i=1;i<TableRows.size();i++){
			String lnk=TableRows.get(i).getText();
			if (lnk.equalsIgnoreCase(linktext)){
				TableRows.get(i).click();
				Reporter.log("Clicked on the table link "+linktext);
				break;
			}
		}
	}


	/* **************************************************************************** */
	/* Method Name : --- ----- --- ClickWebTablink
	 * Purpose     : --- ----- --- Click Web Tab Link
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	 */
	public void clickWebTableLink(By by,String linktext){
		WebElement table=driver.findElement(by);
		List<WebElement> TableRows= table.findElements(By.tagName("tr"));
		for (WebElement cell:TableRows){
			List<WebElement> rowData=cell.findElements(By.tagName("td"));

			for (int i=0;i<rowData.size();i++){
				if (rowData.get(i).findElement(By.tagName("a")).isDisplayed()){
					if (rowData.get(i).findElement(By.tagName("a")).getText().equalsIgnoreCase(linktext)){
						rowData.get(i).findElement((By.tagName("a"))).click();
						break;
					}
				}
			}
		}
	}


	/* **************************************************************************** */
	/* Method Name : --- ----- --- getWebTableRowCount
	 * Purpose     : --- ----- --- get Web Table Row Count
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	 */

	public int getWebTableRowCount(By by){
		WebElement table=driver.findElement(by);
		List<WebElement> TableRows= table.findElements(By.tagName("tr"));
		return TableRows.size();
	}


	/* **************************************************************************** */
	/* Method Name : --- ----- --- getWebTableValue
	 * Purpose     : --- ----- --- get Web Table Value
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	 */

	public String[][] getWebTableValue(By by){
		String[][] tableText;
		WebElement table=driver.findElement(by);
		List<WebElement> TableRows= table.findElements(By.tagName("tr"));
		tableText=new String[TableRows.size()][];
		System.out.println(TableRows.size());
		for (int i=1;i<TableRows.size();i++){
			try{
				List<WebElement> rowData=TableRows.get(i).findElements(By.tagName("td"));
				tableText[i]=new String[rowData.size()];
				System.out.println(rowData.size());
				for (int j=1;j<rowData.size();j++){
					tableText[i][j]=rowData.get(j).getText();
					System.out.println("Value"+rowData.get(j).getText());
				}
			}catch(NoSuchElementException n){
				n.printStackTrace();
			}catch(ElementNotFoundException e){
				e.printStackTrace();
			}
		}
		return tableText;

	}



	/* **************************************************************************** */
	/* Method Name : --- ----- --- getChildElementString
	 * Purpose     : --- ----- --- get Child Element String
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	 */

	public String getChildElementString(By parent,By child,String childText){
		String text="";
		//waitForElement(parent);
		//waitForElement(child);
		System.out.println(childText);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		List<WebElement> ParentObj=driver.findElements(parent);
		for(WebElement we : ParentObj)
			System.out.println("Parent obj "+we.getText());

		List<WebElement> ChildObj=driver.findElements(child);

		for(WebElement we1 : ChildObj) 
			System.out.println("Child obj "+we1.getText());

		System.out.println(ParentObj.size());
		System.out.println(ChildObj.size());
		for (int i=0;i<ParentObj.size();i++){
			System.out.println(ParentObj.get(i).getText());
			if (ParentObj.get(i).getText().contains(childText)){
				System.out.println("Child text "+ChildObj.get(i).getAttribute("id"));
				text=ChildObj.get(i).getText();
				System.out.println("text is"+text);
				Reporter.log("Return vlaue "+text);
				break;
			}
		}
		return text;
	}


	/* **************************************************************************** */
	/* Method Name : --- ----- --- getAllTable
	 * Purpose     : --- ----- --- get All the Tables
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	 */

	public List<WebElement> getAllTable(By by){
		List<WebElement> tbls=driver.findElements(by);
		return tbls;
	}


	/* **************************************************************************** */
	/* Method Name : --- ----- --- returnDay
	 * Purpose     : --- ----- --- Returs the date
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	 */

	public int returnDay(){
		DateFormat dateFormat = new SimpleDateFormat("dd");
		Date date = new Date();
		String day=(String) dateFormat.format(date);
		int Day=Integer.parseInt(day);
		return Day;
	}

	/* ******************************************************************************/
	/* Method Name  : --- ----- --- Verifymandatoryfields
	 * Purpose     : --- ----- --- Verify all mandatory fields symbols are present or not
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public void verifymandatoryfields(){

		List<WebElement> eles=driver.findElements(By.tagName("label"));
		for(WebElement e:eles){
			String else1=e.getAttribute("class");
			if(else1.equalsIgnoreCase("requiredSymbol"))
			{
				System.out.println(e.getAttribute("id"));
				Reporter.log("All mandatory fields has been verified");
			}

		}
	}


	/* ******************************************************************************/
	/* Method Name  : --- ----- --- convertNumberFromString
	 * Purpose     : --- ----- --- converts Number From String
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public int convertNumberFromString(String str){
		int i=Integer.parseInt(str);
		return i;
	}

	/* ******************************************************************************/
	/* Method Name  : --- ----- --- convertdoubleFromString
	 * Purpose     : --- ----- --- converts Number From String
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public double convertdoubleFromString(String str){
		double i=Double.parseDouble(str);
		return i;
	}


	/* ******************************************************************************/
	/* Method Name  : --- ----- --- replaceString
	 * Purpose     : --- ----- --- Replace String
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public String replaceString(String str,String find,String subStr){
		return str.replaceAll(find, subStr);

	}


	/* ******************************************************************************/
	/* Method Name  : --- ----- --- sendKeysAndVerifyText
	 * Purpose     : --- ----- --- sendKeys and Verify Text
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public void sendKeysAndVerifyText(By by,String Text){
		sendKeys(by,Text);
		if (getPropertyValue(by, "value").equalsIgnoreCase(Text)){
			Reporter.log("Value is entered in textbox and The value is present in textbox are equal");
		}else{
			Reporter.log("<font color=\"FF0000\" style=\"background-color:#FFFFFF\">Value is entered in textbox and The value is present in textbox are not equal</font>");
		}
	}



	/* ******************************************************************************/
	/* Method Name  : --- ----- --- isExist
	 * Purpose     : --- ----- --- Verify is Exist 
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public boolean isExist(By by){
		boolean flag=false;
		try{
			if (driver.findElement(by).isDisplayed()){

				flag= true;   
				Reporter.log("<font color=\"0B6121\" style=\"background-color:#FFFFFF\">Element "+by+ "is displayed</font>");
			}else{
				flag=false;
			}
		}catch (Exception Ne){
			Reporter.log("<font color=\"FF0000\" style=\"background-color:#FFFFFF\">Element "+by+ "is not displayed</font>");
			flag= false;
		}
		return flag;
	}


	/* ******************************************************************************/             

	/* Method Name : --- ----- --- verifypricenotexist
	 * Purpose     : --- ----- --- verify same price value should be exist or not
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	 */


	public void verifypricenotexist(By by,String expText){
		waitForElement(by);
		String ImgValue=driver.findElement(by).getText();
		if (ImgValue.equalsIgnoreCase(expText)){
			Reporter.log("Exp price "+expText+" is not displayed so price value has to be changed");
		}else{
			Reporter.log("Exp Text "+expText+" is displayed so price value not to be changed");
		}
	}



	/* ******************************************************************************/

	/* Method Name : --- ----- --- BacktomainBrowser
	 * Purpose     : --- ----- --- BacktomainBrowser
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	 */

	public void BacktomainBrowser(String parentWindow){
		Set<String> winHandles=driver.getWindowHandles();
		for(String winhandle : winHandles)
		{
			if(!winhandle.equals(parentWindow)){
				driver.switchTo().window(parentWindow);
				Reporter.log("The browser window moved to main browser");
			}
		}
	}

	/* ******************************************************************************/

	/* Method Name : --- ----- --- mainBrowser
	 * Purpose     : --- ----- --- mainBrowser
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	 */

	public String mainBrowser(){
		String winHandle=driver.getWindowHandle();
		return winHandle;
	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- clickWebTableLink
	 * Purpose     : --- ----- --- click  WebTable Link
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	 */

	public void clickWebTableLink(By by,By row){
		WebElement table=driver.findElement(by);
		List<WebElement> TableRows= table.findElements(row);
		if (TableRows.get(TableRows.size()).findElement(By.tagName("a")).isDisplayed()){
			TableRows.get(TableRows.size()).findElement(By.tagName("a")).click();

		}
	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- verifyLinks
	 * Purpose     : --- ----- --- verify the Links
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	 */

	public void verifyLinks(By by){
		waitForElement(by);
		WebElement coll=driver.findElement(by);
		List<WebElement> Links=coll.findElements(By.tagName("a"));

		for (WebElement Link:Links){
			System.out.println(Link.getText());
			Reporter.log("expected Link is available the value is "+Link.getText());
		}
	}



	/* ******************************************************************************/

	/* Method Name : --- ----- --- verifyStringContains
	 * Purpose     : --- ----- ---verify String Contains expected value
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	 */

	public boolean verifyStringContains(String act,String exp){
		boolean flag=false;
		if (act.contains(exp)){
			Reporter.log("Actual is equal to expected and value is "+act);
			flag=true;
		}else{
			Reporter.log("Actual is not equal to expected and value is "+act);
		}
		return flag;
	}




	/* ******************************************************************************/

	/* Method Name : --- ----- --- getIndexSpecificElementContains
	 * Purpose     : --- ----- --- get Index Specific Element Contains
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	 */

	public int getIndexSpecificElementContains(By by,String Data){
		int index=-1;
		waitforElementVisible(by);
		waitForElement(by);
		driver.findElements(by);
		List<WebElement> eles=driver.findElements((by));
		System.out.println(eles.size());
		for (int i=0;i<eles.size();i++){
			System.out.println(eles.get(i).getText());
			if (eles.get(i).getText().contains(Data)){
				index=i;
				break;
			}
		}
		return index;

	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- waitForWeb
	 * Purpose     : --- ----- --- waitForWeb
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	 */

	public void waitForWeb(){
		WebDriverWait wait=new WebDriverWait(driver,1000);
		wait.until(ExpectedConditions.titleContains("Android driver webview app"));
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
	}

	/* ******************************************************************************/

	/* Method Name : --- ----- --- getTextasString
	 * Purpose     : --- ----- --- get Text as String
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	 */

	public String getTextasString(By by){

		fluentWait(by);
		String ActualText=driver.findElement(by).getText();
		Reporter.log("Actual text is" +ActualText);
		return ActualText;
	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- listofelements
	 * Purpose     : --- ----- --- get list of elements
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS	 */

	public  List<WebElement> listofelements(By by){
		List<WebElement> ele =driver.findElements(by);
		return ele;
	}


	/* ******************************************************************************/
	/* Method Name : --- ----- --- switchframe
	 * Purpose 	   	: --- ----- --- switch the frame
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public boolean switchframe(String frameid){

		boolean flag = false;
		try
		{
			driver.switchTo().frame(frameid);
			flag = true;
		}catch(Exception e){
			flag= false;
		}
		return flag;
	}


	/* ******************************************************************************/
	/* Method Name : --- ----- --- getWebTableData
	 * Purpose 	   	: --- ----- --- get Web Table Data's
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */

	public String[] getWebTableData(By by,By obj){
		String[] data = null;
		WebElement table=driver.findElement(by);
		List<WebElement> TableRows= table.findElements(obj);
		data = new String[TableRows.size()];
		for (int i=0;i<TableRows.size();i++){
			data[i]=TableRows.get(i).getText();

		}
		return data;

	}


	/* ******************************************************************************/

	/* Method Name : --- ----- --- mousehover
	 * Purpose 	   	: --- ----- --- moves the mouse to a particular element
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public void mousehover(By byValue){
			Actions action = new Actions(driver);
			WebElement we = driver.findElement(byValue);
			action.moveToElement(we).build().perform(); 

	}
	
	/* ******************************************************************************/

	/* Method Name : --- ----- --- mousehover
	 * Purpose 	   	: --- ----- --- moves the mouse to a particular element
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public void mousehover(WebElement ele){
		Actions action = new Actions(driver);
			action.moveToElement(ele).build().perform(); 

	}
	
	/* ******************************************************************************/
	
    /* Method Name  : --- ----- --- click
    * Purpose       : --- ----- --- click on the  element
    * Date Created  : --- ----- --- 
     * Created By   : --- ----- --- TCS
    */

    public void click(WebElement webele) {

           highlight(webele);
           webele.click();
           Reporter.log("clicked on the corresponding element -" +webele);
           waitForPageToLoad(40);
    }

    /* ******************************************************************************/ 

    /* Method Name  : --- ----- --- VerifySpecificElementByProperty
     * Purpose      : --- ----- --- verified whether specific attriubure has expected value 
     * Date Created : --- ----- --- 
     * Created By   : --- ----- --- TCS
    */

    public void VerifySpecificElementByProperty(By by,String prop,String Data){
           //waitForElement(by);
           driver.findElements(by);
           List<WebElement> eles=driver.findElements((by));
           System.out.println(eles.size());
           for (int i=0;i<eles.size();i++){
                  String propvalue=eles.get(i).getAttribute(prop);
                  //System.out.println(eles.get(i).getAttribute(prop));
                  if (propvalue.contains(Data)){
                        Reporter.log("Expected text is present in the attribute "+prop);
                        break;
                  }
           }
    }
    
    /* ******************************************************************************/
	/* Method Name : --- ----- --- getcoordinates
	 * Purpose 	   	: --- ----- --- Get the coordinates of elements
	 * Date Created	: --- ----- --- 
	 * Created By  	: --- ----- --- TCS
	 */
	public Point getcoordinates(By by){
		Actions action = new Actions(driver);
		WebElement ele = driver.findElement(by);
		Point corodinates = ele.getLocation();
		
		return corodinates;
	}
    
	 /* ******************************************************************************/
		/* Method Name : --- ----- --- click radio button
		 * Purpose 	   	: --- ----- ---click the radio button
		 * Date Created	: --- ----- --- 
		 * Created By  	: --- ----- --- TCS
		 */
		public void ClickRadiobutton(By by){
			
			WebElement ele = driver.findElement(by);
			
			ele.click();
			//return corodinates;
		} 


}






