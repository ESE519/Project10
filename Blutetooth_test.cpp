#include <mbed.h>


Serial bt(p9,p10);


char buf[7];


uint8_t i = 0;

char recv[7];
char txs[6] = {'e','s','e','5','1', '9'};
void flush()
{
    
  
     while (bt.readable()) 
             bt.getc();
  
    
}



int main()
{
    bt.baud(115200);
 

    while(1)
    {
       
       

                  
       


       

      printf("s \r\n");
            flush();
      while(!bt.readable());
        printf("\r\n");
       
            while(bt.readable())
            {
        for(i=0;bt.readable();i++){
                       
                buf[i] = bt.getc();
                        
                printf("%c",buf[i]);
               
        }
            }
            
      flush();
        
         for(i=0;i<6;i++){
                     
                     bt.putc(txs[i]);
                 }
            
              flush();
        printf("\r\n");
   
     
       
/***************************/
    }
}