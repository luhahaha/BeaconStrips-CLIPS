# Documenti

La cartella **template** contiene i template dei documenti LaTeX e un documento d'esempio di come vanno preparati.

Per ogni documento va fatta una cartella contenente i file necessari. In particolare:
- se fossero necessarie immagini vanno organizzate in una cartella `img` all'interno della cartella del documento
- vanno lasciati nella cartella tutti e i soli file necessari per la compilazione del pdf e, eventualmente, il pdf stesso. *(se si modifica il file `.tex` il vecchio pdf va rimosso o sostituito con uno nuovo, ma non va lasciato quello vecchio, in modo da mantenere consisteza tra pdf e tex)*


**Mini-guida per l'impostazione dei documenti**

I documenti che andremo a creare saranno mediamente lunghi 50 pagine quindi per una migliore organizzazione credo sia meglio dividere i contenuti di un singolo documento in più file. A seguire delle "regole" per rendere la cosa possibile:

- i vari documenti `NomeDelFile_v0.1.tex` sono già stati creati da Tommaso e messi nelle apposite cartelle. Questi file sono i "main" dei documenti
- per ogni sezione del documento creare un file `nomeSezione.tex` nella stessa cartella del "main".
- includere questi file nell'ordine in cui volete saranno visualizzati nel PDF usando il comando \include{nomeSezione}.
- se vi rendete conto che anche le sottosezioni sono molto grandi create anche queste in file separati denominati `nomeSottosezione.tex` e includete questi nel "main" usando il comando \input{nomeSottosezione}. (in questo caso si usa input e non include così non verrà generata in automatico una nuova pagina alla fine del contenuto del file esterno).

** Per un esempio di queste procedure consultate la cartella PianoDiProgetto **