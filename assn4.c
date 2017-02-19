//CSCI 2500 Assignment 4
//Alexander Denney
#include <stdio.h>

int main()
{
//request hex inputs to sum
	unsigned long a;
	printf("Enter A (hex): \n");
	scanf("%lx", &a);
	unsigned long acopy = a;
  
	unsigned long b;
	printf("Enter B (hex): ");
	scanf("%lx", &b);
    unsigned long bcopy = b;
	printf("\n\n");
  
//Extract binary from input
	int a_input[64];
	int b_input[64];
	int i;
	for(i=0;i<64;i++)
	{
		a_input[i] = a & 1;
		a = a>>1;
		b_input[i] = b & 1;
		b = b>>1;
	}
	int j;

//Print hex then decimal of inputs
	printf("A is ");
	printf("%.16lx", acopy);
	printf(" or %lu\n", acopy);
	
	printf("B is ");
	printf("%.16lx", bcopy);
	printf(" or %lu\n\n", bcopy);
	
//Print input values in binary, reversed order
	printf("Calculate sum, S:\n\nA (bin): ");
	for(int i=63; i>=0; i--)
	{
		printf("%d", a_input[i]);
	}
	printf("\nB (bin): ");
	for(int i=63; i>=0; i--)
	{
		printf("%d", b_input[i]);
	}
	
//Calculate Generates and Propagates from PDF Steps
	int gi[64], pi[64], ggj[16], gpj[16], sgk[4], spk[4], sck[4], gcj[16], ci[64], sumi[64];
	for(int i=0; i<64; i++)
	{
		gi[i] = a_input[i]&b_input[i];
		pi[i] = a_input[i]|b_input[i];
	}

	i = 0;
	for(int j=0; j<16; j++)
	{
		ggj[j]=gi[i+3]|(pi[i+3]&gi[i+2])|(pi[i+3]&pi[i+2]&gi[i+1])|(pi[i+3]&pi[i+2]&pi[i+1]&gi[i]);//SUBBING | FOR + AND & FOR *
		gpj[j]=pi[i+3]&pi[i+2]&pi[i+1]&pi[i];
		//printf("ggj: %d\n\n", ggj[j]);//PRINT OPERANDS
		//printf("gpj: %d\n\n", gpj[j]);//PRINT OPERANDS
		i = i+4;
	}
	
	j = 0;
	for(int k=0; k<4; k++)
	{
		sgk[k] = ggj[j+3] | (gpj[j+3]&ggj[j+2]) | (gpj[j+3]&gpj[j+2]&ggj[j+1]) | (gpj[j+3]&gpj[j+2]&gpj[j+1]&ggj[j]);
		spk[k] = gpj[j+3] & gpj[j+2] & gpj[j+1] & gpj[j];
		j = j + 4;
		//printf("sgk: %d\n\n", sgk[k]);//PRINT OPERANDS
		//printf("spk: %d\n\n", spk[k]);//PRINT OPERANDS
	}
	
	sck[0]= sgk[0] | (spk[0]&0);
	sck[1]= sgk[1] | (spk[1]&sck[0]); 
	sck[2]= sgk[2] | (spk[2]&sck[1]);
	sck[3]= sgk[3] | (spk[3]&sck[2]);
	//printf("sck: %d, 2: %d, 3: %d, 4: %d\n\n", sck[0], sck[1], sck[2], sck[3]);//PRINT OPERANDS
	
	for(j=0; j<16; j++)
	{
		if(j==0)
		{
			gcj[0] = ggj[0] | (gpj[0]&sck[0]);//Hardcode base case
		}
		else
		{
			gcj[j] = ggj[j] | (gpj[j]&gcj[j-1]);
		}
		gcj[j+1] = ggj[j+1] | (gpj[j+1]&gcj[j]);
		gcj[j+2] = ggj[j+2] | (gpj[j+2]&gcj[j+1]);
		gcj[j+3] = ggj[j+3] | (gpj[j+3]&gcj[j+2]);
		//printf("gcj: %d, 2: %d, 3: %d, 4: %d\n\n", gcj[j], gcj[j+1], gcj[j+2], gcj[j+3]);//PRINT OPERANDS
	}
	
	j=0;
	for(i=0; i<16; i++)
	{
		//printf("gi1: %d, 2: %d, 3: %d, 4: %d\n\n", gi[j], gi[j+1], gi[j+2], gi[j+3]);//PRINT OPERANDS
		//printf("pi1: %d, 2: %d, 3: %d, 4: %d\n\n", pi[j], pi[j+1], pi[j+2], pi[j+3]);//PRINT OPERANDS
		if(j==0)
		{
			ci[0] = gi[0] | (pi[0]&gcj[0]);//Hardcode base case
			//ci[0] = 0;
		}
		else
		{
			ci[j] = gi[j] | (pi[j]&ci[j-1]);
		}
		ci[j+1] = gi[j+1] | (pi[j+1]&ci[j]);
		ci[j+2] = gi[j+2] | (pi[j+2]&ci[j+1]);
		ci[j+3] = gi[j+3] | (pi[j+3]&ci[j+2]);
		//printf("%d%d%d%d\n", ci[j], ci[j+1], ci[j+2], ci[j+3]);//PRINT OPERANDS
		j+=4;
	}
	
//Compute sum 
	for(i=0; i<64; i++)
	{
		if (i==0)
		{
			sumi[i]=(0^(a_input[i]^b_input[i]));
			//printf("ci: 0, a: %d, b: %d\n", a_input[i], b_input[i]);
		}
		else
		{
			sumi[i]=(ci[i-1]^(a_input[i]^b_input[i]));
			//printf("ci: %d, a: %d, b: %d\n", ci[i-1], a_input[i], b_input[i]);
		}
	}

//print summation in binary
	printf("\nS (bin): ");
	for(int i=63; i>=0; i--)
	{
		printf("%d", sumi[i]);
	}
	
//Convert binary into decimal 
	unsigned long val = 0;
	for(i=0; i<64; i++)
	{
		unsigned long temp = 1;
		temp = temp << i;
		temp = temp*sumi[i];
		val += temp;
	}
	
//Print summation in hexadecimal and decimal
	printf("\n\nS is ");
	printf("%.16lx", val);
	printf(" or ");
	printf("%lu", val);
}