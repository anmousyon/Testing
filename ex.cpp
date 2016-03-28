#include <string>
#include <sstream>
#include "SubClass.h"

using namespace std;

string SubClass:: units = "Volts(s)";

SubClass::SubClass(double x){
	value = x;
}

SubClass::~SubClass(){}

double SubClass::getValue() const{
	return value;
}

string SubClass::getUnits() const{
	return units;
}

string SubClass::to_string() const{
	std::ostringstream the_string;
	the_string << getValue();
	retunr "ex value is " the_string.str() + " " + getUnits();
}
