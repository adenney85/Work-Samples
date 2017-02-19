#############################################################################
#############################################################################
## Assignment 3: Alexander Denney
#############################################################################
#############################################################################

#############################################################################
#############################################################################
## Data segment
#############################################################################
#############################################################################

.data

matrix_a:    .word 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16
matrix_b:    .word 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16
result:      .word 0, 0, 0, 0, 0, 0, 0, 0, 0,  0,  0,  0,  0,  0,  0,  0

prodmsg:	 .asciiz "Product A x B matrices:\n"
entera:		 .asciiz "Enter the values for matrix A:\n"
enterb:		 .asciiz "Enter the values for matrix B:\n"
newline:     .asciiz "\n"
tab:         .asciiz "\t"


#############################################################################
#############################################################################
## Text segment
#############################################################################
#############################################################################

.text                  # this is program code
.align 2               # instructions must be on word boundaries
.globl main            # main is a global label

.globl matrix_multiply
.globl matrix_print

#############################################################################
main:
#############################################################################
    # alloc stack and store $ra
    sub $sp, $sp, 4
    sw $ra, 0($sp)

    # load A, B, and result into arg regs
    la $a0, matrix_a
    la $a1, matrix_b
    la $a2, result
    jal matrix_multiply

    la $a0, result
    jal matrix_print

    # restore $ra, free stack and return
    lw $ra, 0($sp)
    add $sp, $sp, 4
    jr $ra

##############################################################################
matrix_multiply: 
##############################################################################
# mult matrices A and B together of square size N and store in result.

    # alloc stack and store regs
    sub $sp, $sp, 24
    sw $ra, 0($sp)
    sw $a0, 4($sp)
    sw $a1, 8($sp)
    sw $s0, 12($sp)
    sw $s1, 16($sp)
    sw $s2, 20($sp)

	#store user input for matrix a and b:
	li $s0, 0 #loopa/b counter
	li $s1, 16 #loops comparison
	la $s2, matrix_a
	la $s3, matrix_b
	li $s4, 1 #increment
	
	li $v0, 4 
    la $a0, entera
	syscall	#print matrix a msg
	
	#take 16 user inputs store to matrix a and b
loopa:
	beq $s1, $s0, loopaexit
	addi $v0, $zero, 5
	syscall
	sw $v0, 0($s2)
	add $s2, $s2, 4 
	add $s0, $s0, $s4
	j loopa
	
loopaexit:
	li $s0, 0 #reset loop counter
	li $v0, 4 
    la $a0, enterb
	syscall	#print matrix b msg
loopb:
	beq $s1, $s0, loopbexit
	addi $v0, $zero, 5
	syscall
	sw $v0, 0($s3)
	add $s3, $s3, 4 
	add $s0, $s0, $s4
	j loopb
loopbexit:
	#sentinels
	li $s3, 0 #i
	li $s4, 0 #j
	li $s5, 0 #k
	li $s6, 1 #increment/decrement
	la $s7, result
	li $t0, 0 #icalc
	li $t1, 0 #kcalc
	li $t3, 0 #address A[i][k]
	li $t4, 0 #address B[k][j]
	li $t5, 0 #lw, gets reset
	li $t6, 4 #loop comparison 4
	li $t7, 0 #store t3 * t4
	li $t8, 0 #add results
	li $t9, 0 #store address of array
	
    #setup for i loop, 4 loops
loopi:
	beq $t6, $s3, loopiend
		#setup for j loop, 4*4 loops
	loopj:
		beq $t6, $s4, loopicont
			#setup for k loop, 4*4*4 loops
		loopk:
			beq $t6, $s5, loopjcont 
			# compute A[i][k] address and load into $t3
			sll $t0, $s3, 2		#multiply i loop counter by 4
			add $t3, $t0, $s5	#add k counter to 4*i counter
			# compute B[k][j] address and load into $t4
			sll $t1, $s5, 2		#multiply k loop counter by 4
			add $t4, $t1, $s4	#add j counter to 4*k counter
			#get elements at t3 and t4 then multiply elements
			
			#The following snippet of code will place the value of list[$t3] into the $t3:
			la $t9, matrix_a		# put address of list into $t9
			sll $t3, $t3, 2  	    #index *4
			add $t5, $t9, $t3		# combine the two components of the address
			lw $t3, 0($t5)          # get the value from the array cell
	
			#The following snippet of code will place the value of list[$t4] into the $t4:
			li $t5, 0 
			la $t9, matrix_b		# put address of list into $t9
			sll $t4, $t4, 2  	    #index *4
			add $t5, $t9, $t4		# combine the two components of the address
			lw $t4, 0($t5)          # get the value from the array cell
	
			
			mul $t7, $t3, $t4	#multiply A[i][k] and B[k][j], store in $t7
			add $t8, $t8, $t7	#add $t7's, 64 values down to 16
			# increment k and jump back or exit
			add $s5, $s5, $s6
		j loopk
		#increment j and jump back or exit
		loopjcont: #exit
		li $s5, 0 #reset loopk counter
		#sw result onto matrix, constant is 0. multiply or add by 4 to stack pointer.
        sw $t8, 0($s7)      	# store the value into the array cell
		add $s7, $s7, 4
		
		#Uncomment to print values saved to result array
		#li $v0, 1
		#move $a0, $t8
		#syscall	#print result
		#li $v0, 4 
        #la $a0, tab
		#syscall	#print a tab
		
		li $t7, 0 
		li $t8, 0 #clear registers with results
		add $s4, $s4, $s6 #increment loopj counter
	j loopj #exit loopj
    #increment i and jump back or exit
	loopicont:
	li $s4, 0 #reset loopj counter
	add $s3, $s3, $s6	#increment loopi counter
j loopi
loopiend:
    # retore saved regs from stack
    lw $s2, 20($sp)
    lw $s1, 16($sp)
    lw $s0, 12($sp)
    lw $a1, 8($sp)
    lw $a0, 4($sp)
    lw $ra, 0($sp)

    # free stack and return
    add $sp, $sp, 24
    jr $ra

##############################################################################
matrix_print:
##############################################################################

    # alloc stack and store regs.
    sub $sp, $sp, 16
    sw $ra, 0($sp)
    sw $s0, 4($sp)
    sw $s1, 8($sp)
    sw $a0, 12($sp)
	
	#li sentinels, reset to 0
	li $s3, 0 #loopm counter
	li $s4, 4 #4th var in row
	li $s5, 1 #increment/decrement
	li $s6, 4 #loopm comparison
	li $s7, 0 #loop4 counter
	li $t3, 0 #load result array
	li $t4, 0 #value at result[$t3]
	li $t5, 0 #result index
	li $t7, 0
	
    li $t0, 4 #size of array
	li $t2, 0 #store result from stack
	
    # do your two loops here
	#loop through array	
		#loop through 4 then tab
	la $t6, result
	li $v0, 4 
    la $a0, prodmsg
	syscall
loopm:
	beq $s6, $s3, exitloopm #loop 4 times
	loop4:
		beq $s4, $s7, loopmcont #loop 4*4 times
		#The following snippet of code will place the value of list[$s3] into the $t4:
		move, $t5, $t7
		sll $t5, $t5, 2  	    #index *4
		add $t5, $t5, $t6		# combine the two components of the address
		lw $t3, 0($t5)          # get the value from the array cell
		add $t7, $t7, $s5
		li $v0, 1
		move $a0, $t3
		syscall	#print result[$s3]
		
		li $v0, 4 
        la $a0, tab
		syscall	#print a tab
		
		add $s7, $s5, $s7 #increment loop4 counter
	j loop4
loopmcont:
	li $v0, 4 
    la $a0, newline
	syscall #print newline
	add $s3, $s3, $s5 #increment loopm counter
	li $s7, 0 #reset loop4 counter
j loopm
exitloopm:
    # setup to jump back and return
    lw $ra, 0($sp)
    lw $s0, 4($sp)
    lw $s1, 8($sp)
    lw $a0, 12($sp)
    add $sp, $sp, 16
    jr $ra

