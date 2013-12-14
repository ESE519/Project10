#include "mbed.h"

 
SPI spi(p11, p12, p13); // mosi, miso, sclk
DigitalOut cs1(p20);
DigitalOut cs2(p19);
DigitalOut cs3(p18);

Serial pc(p28, p27);

uint8_t flag;
int data4[] =
{
 0,0,0,2,2,0,0,0,
 0,0,2,2,2,2,0,0,
 0,2,2,2,2,2,2,0,
 2,2,2,2,2,2,2,2,
 0,0,0,2,2,0,0,0,
 0,0,0,2,2,0,0,0,
 0,0,0,2,2,0,0,0,
 0,0,0,2,2,0,0,0,
};



void funct(DigitalOut cs)
{
    
    cs = 0;
        for(int i = 0 ; i<64 ; i++)
        spi.write(data4[i]);

    cs = 1;
    wait_ms(500);
    cs = 0;
        for(int i = 0 ; i<64 ; i++)
        spi.write(0);
    cs = 1;
    wait_ms(500);
}

void rcv()
{
    uint8_t rc1 = pc.getc();
    
    printf("rci is %d \r\n",rc1);
    if(rc1 == 1)
    {
        flag = 1;
    }
    else if(rc1 == 2)
    {
        flag = 2;
    }
    else if(rc1 == 3)
    {
        flag = 3;
    }
    
    if(flag == 1)
        funct(cs1);
    
    else if(flag == 2)
        funct(cs2);
    
    else if(flag == 3)
        funct(cs3);
};

int main() {
    cs1 = 1;
        cs2 = 1;
      cs3 = 1;
    spi.format(8,0);
    spi.frequency(1000000);
    // Chip must be deselected

 



    
     wait_ms(0.5);
   
 
pc.attach(&rcv);

while(1);



}
    
    


}