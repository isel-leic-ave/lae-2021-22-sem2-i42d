// This  is exported from the Point.lib

typedef struct point Point;

struct point {
    int z, x, y;
};

/**
 * @brief Initializes the Point instance with the values of x and y.
 */
void Point_init(Point* p, int x, int y);

/**
 * @brief Allocates memory for a Point instance and initiazes it.
 */
Point* Point_new(int x, int y);

/**
 * @brief Free memory.
 */
void Point_delete(Point* p);

double Point_module(Point* p);

void Point_print(Point* p);
