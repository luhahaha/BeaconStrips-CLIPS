/* 
 * Rileva quali parole presenti nel glossario non sono state marcate \gl{parola} nei file nomeFile.tex
 * 
 * Il programma deve essere presente nella stessa cartella del documento .tex da analizzare, 
 * inoltre deve essere presente nella stessa cartella il file 'termini.txt' contenente tutti i termini 
 * del glossario con prima lettera minuscola e maiuscola.
 *
 * Diario delle modifiche:
 * 2016/03/20 - Viviana Alessio - Creazione programma.
 *
 */ 
 
#include <iostream>
#include <fstream>
#include <string>
using std::cin;
using std::cout;
using std::ifstream;
using std::ofstream;
using std::endl;
using std::string;

bool match(char *termine, char *parola){
	bool trovata = false, esci = false;
	int i=0, j=0;
	while(parola[i] && !trovata && !esci){
		//controllo che la parola non sia già glossarizzata (preceduta da \gl{)
		if(parola[0]==92 && parola[1]=='g' && parola[2]=='l' && parola[3]=='{')
			esci = true;
		else{
			// se l'inizio coincide cerco di trovare la parola completa
			while(termine[j] && parola[i] == termine[j]){
				j++; 
				i++;
			}
			// parola trovata
			if(!termine[j])
				trovata = true;
			// mismatch. provo ad avanzare di un carattere (potrebbe succedere se la parola è dentro un comando latex)
			if(parola[i] != termine[j]){
				i++; j=0;
			}
		}
	}
	return trovata;
}

int main(int argc, char const *argv[]){
	string file;
	cout<<"inserire nome.tex del file da analizzare (presente nella cartella)"<<endl;
	cin>>file;
	
	ifstream termini("termini.txt", std::ios::in);
	ifstream fileDaControllare(file.c_str(), std::ios::in);

	// non funziona perchè mi cancella il contenuto del file 
	//ofstream fileDaScrivere(file.c_str());

	while(!termini.eof()){
		char termine[30];
		termini>>termine;
		fileDaControllare.open(file.c_str(), std::ios::in);
		fileDaControllare.clear();
		while(!fileDaControllare.eof()){
			char parolaTex[200];
			fileDaControllare>>parolaTex;
			//cout<<endl<<parolaTex<<endl;
			if(match(termine,parolaTex))
	        	cout<<"glossarizza "<<parolaTex<<endl;
    	}
	    fileDaControllare.close();
	}
	termini.close(); 
} 
