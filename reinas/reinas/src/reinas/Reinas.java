/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package reinas;
import java.io.*;
import static java.lang.Math.random;
import java.util.*;

/**
 *
 * @author Avo
 */
public class Reinas {

    int numero_individuos;
    int numero_generaciones;
    int total_elitista;
    double prob_cruza_max;
    double prob_cruza_min;
    double prob_mutacion_max;
    double prob_mutacion_min;
    double prob_cruza;
    double prob_mutacion;
    double seleccion_elitista;
    public Reinas(){
       numero_individuos=50;
       prob_cruza_max=0.9;
       prob_cruza_min=0.5;
       prob_mutacion_max=0.2;//0.2
       prob_mutacion_min=0.05;    
       seleccion_elitista=0.1;//10%
       numero_generaciones=50;
    }
    
    public boolean verificar(int []posReina,int pos_mutacion){
        int numero=0;
        if(pos_mutacion==0||pos_mutacion==3||pos_mutacion==6||pos_mutacion==9||pos_mutacion==12||pos_mutacion==15)
        {
         numero=a_base_diez(posReina[pos_mutacion]+1,posReina[pos_mutacion+1],posReina[pos_mutacion+2]);
        }
        if(pos_mutacion==1||pos_mutacion==4||pos_mutacion==7||pos_mutacion==10||pos_mutacion==13||pos_mutacion==16)
        {
          numero=a_base_diez(posReina[pos_mutacion-1],posReina[pos_mutacion]+1,posReina[pos_mutacion+1]);
        }
        if(pos_mutacion==2||pos_mutacion==5||pos_mutacion==8||pos_mutacion==11||pos_mutacion==14||pos_mutacion==17)
        {        
            numero=a_base_diez(posReina[pos_mutacion-2],posReina[pos_mutacion-1],posReina[pos_mutacion]+1);
        }
        return numero<=5;
    }
    public int a_base_diez(int a,int b,int c){
        return a*4+b*2+c;
    }
    public double aleatorio_double(){
        Random r=new Random();
        double aux=r.nextDouble();
        aux=aux*100;
        int aux2=(int)(aux);
        aux=(double)(aux2)/100;
        return aux;
    }
    public void mutacion(int []posReina){
        boolean a=true;
        Random r=new Random();
        int pos_mutacion;
        while(a){
            pos_mutacion=r.nextInt(18);
            if(posReina[pos_mutacion]==1){
                posReina[pos_mutacion]=0;
                a=false;
            }else{
                if(verificar(posReina,pos_mutacion)){
                   posReina[pos_mutacion]=1;
                   a=false;
                }
            }
        }
            
    }
    public void cruza(ArrayList<Individuo> poblacion,ArrayList<Individuo>  poblacion_aux,int posicion_padre1,int posicion_padre2){
        Individuo I=new Individuo();
        Random r=new Random();
        //Primer descendiente
        int pos_cruza=r.nextInt(6)*3;//puede ser 0,3,6,9,15
        int j=0;
        int pos_mutacion;
        if (pos_cruza==0){pos_cruza=2;}else{pos_cruza--;}
        for(j=0;j<=pos_cruza;j++){
             I.posReina[j]=poblacion.get(posicion_padre1).posReina[j];
        }
        for(int h=j;h<=17;h++){
             I.posReina[h]=poblacion.get(posicion_padre2).posReina[h];
        }
        //Mutacion
        prob_mutacion=aleatorio_double();
        if(prob_mutacion>=prob_mutacion_min && prob_mutacion<=prob_mutacion_max){
            mutacion(I.posReina);
        }
        poblacion_aux.add(I);
        
        //segundo descendiente
        pos_cruza=r.nextInt(6)*3;
        j=0;
        if (pos_cruza==0){pos_cruza=2;}else{pos_cruza--;}
        for(j=0;j<=pos_cruza;j++){
           I.posReina[j]=poblacion.get(posicion_padre1).posReina[j];
        }
        for(int h=j;h<=17;h++){
           I.posReina[h]=poblacion.get(posicion_padre2).posReina[h];
        }
        prob_mutacion=aleatorio_double();
        if(prob_mutacion>=prob_mutacion_min && prob_mutacion<=prob_mutacion_max){
            mutacion(I.posReina);
        }
        poblacion_aux.add(I);
    }
    public void seleccion_ruleta(ArrayList<Individuo> poblacion,ArrayList<Individuo>  poblacion_aux ){
       Random r = new Random();
       int posicion_padre1;
       int posicion_padre2;
       int pos_cruza;

        for(int i=0;i<(numero_individuos-total_elitista)/2;i++){
            posicion_padre1=r.nextInt(numero_individuos);
            posicion_padre2=r.nextInt(numero_individuos);
            prob_cruza=aleatorio_double();

            if(prob_cruza>=prob_cruza_min && prob_cruza_min<=prob_cruza_max){
                cruza(poblacion,poblacion_aux,posicion_padre1,posicion_padre2);
            }           
            else{
                //no cruza
                poblacion_aux.add(poblacion.get(posicion_padre1));
                poblacion_aux.add(poblacion.get(posicion_padre2));
            }
        }
        

    }
    public void seleccion_elitista(ArrayList<Individuo> poblacion,ArrayList<Individuo>  poblacion_aux ){
       
       total_elitista=(int)(seleccion_elitista*numero_individuos);
       if(total_elitista%2!=0){
           total_elitista++;
       }
       for(int i=0;i<total_elitista;i++){
            poblacion_aux.add(poblacion.get(i));
       }
       

    }
    public void mostrar_aptitud(ArrayList<Individuo> poblacion ){
        for(int i=0;i<numero_individuos;i++){
           System.out.println(poblacion.get(i).getAptitud());
        }   
       
    }

    public void ordenar_aptitud(ArrayList<Individuo> poblacion ){
        //mostrar_aptitud(poblacion);
        Collections.sort(poblacion, new Comparator<Individuo>() {
        
            @Override
            public int compare(Individuo o1, Individuo o2) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
               return new Integer(o1.getAptitud()).compareTo(new Integer(o2.getAptitud()));


            }
        });
    }
	
    
    
   
    public void inicializar_tablero(int [][]tablero){
        for(int i=0;i<=5;i++){//inicializar tablero
            for(int j=0;j<=5;j++){
                tablero[i][j]=0;
            }
        }
    }
    public int comer_reina(int [][]tablero){
        int contador=0;int aux_i,aux_j;
        for(int i=0;i<=5;i++){
           for(int j=0;j<=5;j++){
              if(tablero[i][j]==1){
                  //buscar reinas vertical
                  for(int h=0;h<=5;h++){                      
                      if(tablero[h][j]==1 && h!=i){
                          contador++;
                          break;
                      }                     
                  }
                  //en horizontal no hay
                  //diagonal hacia arriba izquierda
                  aux_i=i;aux_j=j;
                  while(aux_i>=1 && aux_j>=1){                      
                      aux_i--;
                      aux_j--;
                      if(tablero[aux_i][aux_j]==1){
                          contador++;
                          break;
                      }                      
                  }  
                  //diagonal hacia arriba derecha
                  aux_i=i;aux_j=j;
                  while(aux_i>=1 && aux_j<=4){                      
                      aux_i--;
                      aux_j++;
                      if(tablero[aux_i][aux_j]==1){
                          contador++;
                          break;
                      }                      
                  }  
                  aux_i=i;aux_j=j;//diagonal abajo derecha
                  while(aux_i<=4 && aux_j<=4){    
                      aux_i++;
                      aux_j++;
                      if(tablero[aux_i][aux_j]==1){
                          contador++;
                          break;

                      }
                    
                  }
                   aux_i=i;aux_j=j;//diagonal abajo izquierda
                  while(aux_i<=4 && aux_j>=1){    
                      aux_i++;
                      aux_j--;
                      if(tablero[aux_i][aux_j]==1){
                          contador++;
                          break;

                      }
                    
                  }
              }
           }
        }       
        return contador;
    }
    public void calcular_aptitud(ArrayList<Individuo> poblacion ){
        int []pos_reinas=new int[10];
        int fila=0,columna=0;
        int [][]tablero=new int[6][6];
        for(int i=0;i<numero_individuos;i++){
            inicializar_tablero(tablero);
            for(int j=0;j<=5;j++){
                columna=a_base_diez(poblacion.get(i).posReina[j*2+j],poblacion.get(i).posReina[j*2+j+1],poblacion.get(i).posReina[j*2+j+2]);
                fila=j;
                if(columna>5){
                    //tem.out.println("columa "+columna+" "+j+" "+i);
                }
                tablero[fila][columna]=1;
            }
          
            poblacion.get(i).setAptitud(comer_reina(tablero));
           //System.out.println(poblacion.get(i).getAptitud());
        }
    }
    
    public void crear_poblacion(ArrayList<Individuo> poblacion ){
        for(int i=0;i<numero_individuos;i++){   
            Individuo n= new Individuo();     
            poblacion.add(n);
        }
    
    }

    
    public void mostrar_poblacion(ArrayList<Individuo> poblacion ){
    for(int i=0;i<numero_individuos;i++){   
        System.out.print(poblacion.get(i).getAptitud()+"    ");	
        System.out.print(poblacion.get(i).posReina[0]);	
        System.out.print(poblacion.get(i).posReina[1]);	
        System.out.print(poblacion.get(i).posReina[2]+" ");	
        System.out.print(poblacion.get(i).posReina[3]);	
        System.out.print(poblacion.get(i).posReina[4]);	
        System.out.print(poblacion.get(i).posReina[5]+" ");	
        System.out.print(poblacion.get(i).posReina[6]);	
        System.out.print(poblacion.get(i).posReina[7]);	
        System.out.print(poblacion.get(i).posReina[8]+" ");	
        System.out.print(poblacion.get(i).posReina[9]);	
        System.out.print(poblacion.get(i).posReina[10]);	
        System.out.print(poblacion.get(i).posReina[11]+" ");	
        System.out.print(poblacion.get(i).posReina[12]);	
        System.out.print(poblacion.get(i).posReina[13]);	
        System.out.print(poblacion.get(i).posReina[14]+" ");	
        System.out.print(poblacion.get(i).posReina[15]);
        System.out.print(poblacion.get(i).posReina[16]);	
        System.out.print(poblacion.get(i).posReina[17]+" ");	
        System.out.println();
    }
    }
    public void principal(ArrayList<Individuo> poblacion){
        Reinas R = new Reinas();
        ArrayList<Individuo> poblacion_aux; 
        poblacion_aux = new ArrayList<>();
        
        R.crear_poblacion(poblacion);
        int iteraciones=0;
        while(iteraciones<=numero_generaciones){
           System.out.println("*******************************"); 
           System.out.println("Generacion "+iteraciones); 	
           System.out.println("*******************************"); 	

            R.calcular_aptitud(poblacion);
            R.ordenar_aptitud(poblacion);
                       
            R.mostrar_poblacion(poblacion);

            //System.out.println(poblacion.get(0).getAptitud()+" "+ poblacion.get(0).posReina);
            if(poblacion.get(0).getAptitud()==0){
                System.out.println("Individuo optimo en la generacion: "+iteraciones);
                break;
            }
            R.seleccion_elitista(poblacion,poblacion_aux);
            R.seleccion_ruleta(poblacion,poblacion_aux);
            poblacion.clear();
            for(int index=0;index<numero_individuos;index++){
                poblacion.add(poblacion_aux.get(index));
            }
            poblacion_aux.clear();
            iteraciones++;
   

        }if(iteraciones>numero_generaciones){
           System.out.println("No se encontro al individuo optimo");

        }
    }
    
}
