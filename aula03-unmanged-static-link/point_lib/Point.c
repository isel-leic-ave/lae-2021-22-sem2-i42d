// Point.c : Defines the exported functions
//
#include "Point.h"
#include <math.h>
#include <stdio.h>
#include <stdlib.h>

void Point_init(Point* p, int x, int y) {
	p->x = x;
	p->y = y;
	p->z = 0;
}

Point* Point_new(int x, int y) {
	Point* p = malloc(sizeof(Point));
	Point_init(p, x, y);
	return p;
}

void Point_delete(Point* p) {
	free(p);
}

double Point_module(Point* p) {
	return sqrt((double) p->x * p->x + p->y * p->y + p->z * p->z);
}

void Point_print(Point* p) {
	printf("Version 2 - Point (x = %d, y = %d, z = %d)\n", p->x, p->y, p->z);
}
