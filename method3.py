import math

#Divide and conquer algorithm method 3
def dandc(x,y):
    n = max(x.bit_length(),y.bit_length())
    if n==1:
        return(x&y)
    if ((x == 0) or (y == 0)):
        return 0
    xlmask = ((1 << int(n / 2)) - 1)
    ylmask = ((1 << int(n / 2)) - 1)
    xl = (x >> (int(n/2)))
    xr = xlmask&x
    yl = y >> (int(n/2))
    yr = ylmask&y

    p1=dandc(xl,yl)
    p2=dandc(xr,yr)
    xa=(xl+xr)
    yb=(yl+yr)
    p3=dandc(xa,yb)
    m = n >> 1
    return (((p1)<<(m<<1))+((p3-p1-p2)<<m) + p2)

x = input('input x to multiply by y:')
y = input('input y:')
print(dandc(x, y))