
package reinas;
import java.util.*;

public class Individuo {
  
  public    int[]   posReina={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17};
  int aptitud;    
    
  public int a_base_diez(int a,int b,int c){
        return a*4+b*2+c;
  }
public Individuo() {    
    Random random = new Random();
    //reina 1
    int a,b,c;
    int num_reina=0;
    while(num_reina<6){
        a=random.nextInt(2);
        b=random.nextInt(2);
        c=random.nextInt(2);
        if(a_base_diez(a,b,c)<=5){
            posReina[num_reina*2+num_reina]=a;
            posReina[num_reina*2+num_reina+1]=b;
            posReina[num_reina*2+num_reina+2]=c;
            num_reina++;
        }
    }
    
      
}
    
    public void colocaPosiciones()
   
    {
    	 
    
    }    
    public int  getAptitud()
    {
        return aptitud;
    }
    
    public void  setAptitud(int aptitud)
    {
        this.aptitud=aptitud;
    }
    
    public int[] getPosReinas()
    {
    	return posReina;
    }
    
   

}
    
    
