#include <nrk.h>
#include <include.h>
#include <ulib.h>
#include <stdio.h>
#include <stdlib.h>
#include <hal.h>
#include <nrk_error.h>
#include <nrk_timer.h>
#include <nrk_stack_check.h>
#include <nrk_stats.h>
#include <string.h>
#include "mbed.h"
#include "basic_rf.h"
#include "bmac.h"
#include <nrk_driver_list.h>
#include <nrk_driver.h>
#include <ff_basic_sensor.h>


/******* Inputs and Outputs *******/
AnalogIn start_s(p20);
DigitalIn ramhome_ps(p21);
DigitalIn ramout_ps(p22);
DigitalIn worktable_ps(p23);
DigitalIn hmout_ps1(p26);
DigitalIn hmout_ps2(p24);
DigitalIn hmhome_ps(p25);
//DigitalIn acupper_ps(p30); //p27
//DigitalIn aclower_ps(p26);
DigitalIn vmupper_ps(p5); //p28
DigitalIn vmlower_ps(p18);

DigitalOut ram_en(p16);
DigitalOut ram_cw(p17);
DigitalOut conv_en(p19);
DigitalOut worktable_m(p15);

DigitalOut hm_en(p13);
DigitalOut hm_cw(p14);
//DigitalOut hmdrill_en(p12);
DigitalOut pwm3(p8);
DigitalOut pwm4(p7);

DigitalOut vm_cw(p11);
DigitalOut vm_en(p10);
//DigitalOut vmdrill_en(p9);
DigitalOut pwm2(p9);

//DigitalOut ac_cw(p8);
//DigitalOut ac_en(p7);
//DigitalOut ac_led_en(p6);
DigitalOut pwm1(p12);


DigitalOut led1(LED1);
DigitalOut led2(LED2);
DigitalOut led3(LED3);
DigitalOut led4(LED4);



/***********************************/


/************** Flags **************/
int flag_busy = 0;
int flag_sched = 0;
//int flag_conv_in = 0;
int flag_conv_out = 0;
int flag_hm1 = 0;
int flag_hm2 = 0;
int flag_vm = 0;
int flag_ac = 0;
int wt_posn = 1;
int flag = 0;
int i;
/***********************************/




/*********** Variables *************/
char rx_buf[RF_MAX_PAYLOAD_SIZE];
uint8_t arr[5];// = {2,1,3}; // changed 7 to len 3
uint8_t arr_pos = 0;
#define tasks 5
int tilt = 1700;
/***********************************/


/************ Solid PWM ************/
void sol_pwm(DigitalOut pwm, int r_turn, int l_turn)
{
        for(i=0;i<10;i++)
        {
                pwm = 1;
                wait_us(r_turn);
                pwm = 0;
                wait_ms(26);
        }
        for(i=0;i<10;i++)
        {
                pwm = 1;
                wait_us(1525);
                pwm = 0;
                wait_ms(26);
        }    
        for(i=0;i<10;i++)
        {
                pwm = 1;
                wait_us(l_turn);
                pwm = 0;
                wait_ms(26);
        }
        for(i=0;i<10;i++)
        {
                pwm = 1;
                wait_us(1525);
                pwm = 0;
                wait_ms(26);
        }
}
/***********************************/



/************ Liquid PWM ************/
void liq_pwm(DigitalOut pwm, int drop)
{
    for(i=1000;i<drop;i=i+25)
    {
        pwm = 1;
        wait_us(i);
        pwm = 0;
        wait_ms(26);
    }
    wait(3);

    for(i=0;i<40;i++)
    {
        pwm= 1;
        wait_us(1300);
        pwm = 0;
        wait_ms(26);
    }
    wait(3);
    
    for(i=1300;i>1000;i=i-25)
    {
        pwm= 1;
        wait_us(i);
        pwm = 0;
        wait_ms(26);
    }    
    wait(7);
}
/***********************************/



Serial rcv(p28,p27);

void flush()
{
    
 
     while (rcv.readable())
             rcv.getc();
 
    
}


void recv()
{
   
if(flag == 0)
{    
   printf("s \r\n");
   while(!rcv.readable());
   printf("\r\n");     
    for(i=0;rcv.readable();i++){
                        
                arr[i] = rcv.getc() - 48;
                         
                printf("%d",arr[i]);
                         
        }
                flag = 1;
        printf("end recv\r\n");
 
}                
}


void worktable()
{
    /******** Worktable ********/
    worktable_m = 1;
    wait(0.75);
    while(!worktable_ps);
    wait(0.15);
    worktable_m = 0;
    wait(2);
    wt_posn = (wt_posn % 4) + 1;
}


void sched()
{
    switch(arr[arr_pos])
    {
        case 1:
        {
            flag_ac = 1;
            while(wt_posn != 2)
            {
                worktable();
            }
            break;
        }
        case 2:
        {
            flag_vm = 1;
            while(wt_posn != 3)
            {
                worktable();
            }
            break;
        }
        case 3:
        {
            flag_hm1 = 1;
            while(wt_posn != 4)
            {
                worktable();
            }
            break;
        }
                 case 4:
        {
            flag_hm2 = 1;
            while(wt_posn != 4)
            {
                worktable();
            }
            break;
        }
        default:
        {            
            arr_pos++;    
            break;
        }

    }
        
}



int main()
{
    rcv.baud(115200);
    while (1)
    {
        
        recv();
        if(flag == 1)
        {
            
            float val1[3] ;

            val1[0] = start_s.read();
            val1[1] = start_s.read();
            val1[2] = start_s.read();
            //printf("The val is %f : %f :%f " , val1[0],val1[1],val1[2]);

            if(val1[0] < 0.4 && val1[1] < 0.4 && val1[2] < 0.4) {
                             // flush();
                              rcv.printf("P");
                             // flush();
                printf("Scheduling the tasks \r\n");
                flag = 0;  
                
                /******** Conveyor ********/
                conv_en = 1;
                wait(4.0);
                conv_en = 0;
                wait(1);
             
             
                /******** Ram Out ********/
                ram_cw = 1;
                while(ramout_ps) {
                        ram_en = 1;
                }
                wait(0.2);
                                ram_cw = 0;
                                wait(0.2);
                ram_en = 0;
                wait(2);
            
                            
                while(arr_pos != tasks)
                {
                    sched();
                    
                    if(flag_hm1 == 1)
                    {
                                            
                                    //          flush();
                                              rcv.printf("S");
                        flag_hm1 = 0;
                        
                        /******** Horizontal Mill Out ********/
                        hm_cw = 1;
                        while(hmout_ps1) {
                                hm_en = 1;
                        }
                        hm_en = 0;

                        wait(0.5);
                     
                     
                        /******** Horizontal Mill Drill ********/
                                                for(i=0;i<10;i++)
                                                {
                                                        pwm3 = 1;
                                                        wait_us(1000);
                                                        pwm3 = 0;
                                                        wait_ms(26);
                                                }
                                                for(i=0;i<10;i++)
                                                {
                                                        pwm3 = 1;
                                                        wait_us(1500);
                                                        pwm3 = 0;
                                                        wait_ms(26);
                                                }    
                                                for(i=0;i<10;i++)
                                                {
                                                        pwm3 = 1;
                                                        wait_us(2000);
                                                        pwm3 = 0;
                                                        wait_ms(26);
                                                }
                                                for(i=0;i<10;i++)
                                                {
                                                        pwm3 = 1;
                                                        wait_us(1500);
                                                        pwm3 = 0;
                                                        wait_ms(26);
                                                }    
                        
                                                
                                                     /******** Horizontal Mill Home ********/
                        if(arr[arr_pos+1] != arr[arr_pos])
                                                {
                                                        hm_cw = 0;
                                                        while(hmhome_ps) {
                                                                        hm_en = 1;                        
                                                        }
                                                        hm_en = 0;
                                                }
                                                
                        if(arr_pos != tasks)
                        {
                            arr_pos++;
                        }
                    }
                                        
                                        if(flag_hm2 == 1)
                    {
                                            
                                    //          flush();
                                              rcv.printf("S");
                        flag_hm2 = 0;
                        
                        /******** Horizontal Mill Out ********/
                        hm_cw = 1;
                        while(hmout_ps2) {
                                hm_en = 1;
                        }
                        hm_en = 0;

                        wait(0.5);
                     
                     
                        /******** Horizontal Mill Drill ********/
                                                for(i=0;i<10;i++)
                                                {
                                                        pwm4 = 1;
                                                        wait_us(1000);
                                                        pwm4 = 0;
                                                        wait_ms(26);
                                                }
                                                for(i=0;i<10;i++)
                                                {
                                                        pwm4 = 1;
                                                        wait_us(1500);
                                                        pwm4 = 0;
                                                        wait_ms(26);
                                                }    
                                                for(i=0;i<10;i++)
                                                {
                                                        pwm4 = 1;
                                                        wait_us(2000);
                                                        pwm4 = 0;
                                                        wait_ms(26);
                                                }
                                                for(i=0;i<10;i++)
                                                {
                                                        pwm4 = 1;
                                                        wait_us(1500);
                                                        pwm4 = 0;
                                                        wait_ms(26);
                                                }    
                        
                                                
                                                     /******** Horizontal Mill Home ********/
                        if(arr[arr_pos+1] != arr[arr_pos])
                                                {
                                                        hm_cw = 0;
                                                        while(hmhome_ps) {
                                                                        hm_en = 1;                        
                                                        }
                                                        hm_en = 0;
                                                }
                                                
                        if(arr_pos != tasks)
                        {
                            arr_pos++;
                        }
                    }                                        

                                         
                    if(flag_vm == 1)
                    {
                                        //      flush();
                                              rcv.printf("H");
                        flag_vm = 0;
                        
                        /******** Vertical Mill Down ********/
                        vm_cw = 1;
                        while(vmlower_ps) {
                                vm_en = 1;
                        }
                        vm_en = 0;

                        wait(0.5);
                     
                     
                        /******** Vertical Mill Drill ********/
                           for(i=0;i<10;i++)
                                                {
                                                        pwm2 = 1;
                                                        wait_us(1000);
                                                        pwm2 = 0;
                                                        wait_ms(26);
                                                }
                                                for(i=0;i<10;i++)
                                                {
                                                        pwm2 = 1;
                                                        wait_us(1500);
                                                        pwm2 = 0;
                                                        wait_ms(26);
                                                }    
                                                for(i=0;i<10;i++)
                                                {
                                                        pwm2 = 1;
                                                        wait_us(2000);
                                                        pwm2 = 0;
                                                        wait_ms(26);
                                                }
                                                for(i=0;i<10;i++)
                                                {
                                                        pwm2 = 1;
                                                        wait_us(1500);
                                                        pwm2 = 0;
                                                        wait_ms(26);
                                                }    
                     

                        /******** Vertical Mill Up ********/
                        if(arr[arr_pos+1] != arr[arr_pos])
                                                {
                                                        vm_cw = 0;
                                                        while(vmupper_ps) {
                                                                        vm_en = 1;
                                                        }
                                                        vm_en = 0;
                                                }
                        
                        if(arr_pos!=tasks)
                        {
                            arr_pos++;
                        }
                    }

                 
                 
                    if(flag_ac == 1)
                    {
                                          //  flush();    
                                              rcv.printf("C");
                        flag_ac = 0;
                                                liq_pwm(pwm1,tilt);
                        tilt = tilt + 23;    
                                                
                        if(arr_pos != tasks)
                        {
                            arr_pos++;
                        }
                    }
                    
                }

                arr_pos = 0;
                while(wt_posn != 1)
                    worktable();
                
                                rcv.printf("R");
                /******** Ram Home ********/
                ram_cw = 0;
                while(ramhome_ps) {
                        ram_en = 1;
                }
                wait(1);
                ram_en = 0;
                                    
                conv_en = 1;
                wait(4.5);
                conv_en = 0;          
            }
        
        }
       
    }
    
}