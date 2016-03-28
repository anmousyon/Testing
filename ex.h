#include <string>
#include <sstream>
#include "SuperClass.h"

using namespace std;

class SubClass: public SuperClass{

private:
	static string units;
	double value;

public:
	SubClass(double x);
	~SubClass();
	double getValue() const;
	string getUnits() const;
	sting to_string() const;
};
