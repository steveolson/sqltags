// FILENAME:     utilDate.js

// DESCRIPTION:  Drop-down date utility functions

// CONTENTS:     This file contains utility functions useful for the JavaScript Date data type

// AUTHORS:      Benjamin Lindahl (Ben@AIT-INC.com)
//               Gurney Thompson (Gurney@AIT-INC.com)

// COMPANY:      Applied Information Technologies, Inc. (http://www.ait-inc.com/)

// PROJECT:      This file is part of the JS-Lib (JavaScript Library) portion of the AITWorks project

// VERSION:      JS-Lib 0.0.1

// CO-REQUISITE INCLUDES/FILES: util.js

// SPECIAL USAGE INSTRUCTIONS: None

// FUNCTION CATEGORIES:
//   -- General Date Utility Functions:     Get general information about dates
//   -- Date Logic and Arithmetic:          Arithmetic and logic for the JavaScript Date type

// PARTICULARLY USEFUL FUNCTIONS:
//   General Date Utility Functions:
//   -- getDaysInMonth:                     Return the number of days in the given month of the given year
//   -- hourOfDay:                          Return the hour of the day, based upon the hour and AM/PM value passed in
//   -- xxxxFromDate:                       Return the xxxx portion of the Date datatype, where xxxx is year, month, day, hour, hourOfDay, minute, second, or am
//
//   Date Logic and Arithmetic:
//   -- dateAdd:                            Add a certain number of years, months, days, hours, minutes, and seconds to a Date
//   -- xxxxAdd:                            Add a certain number of xxxx, where xxxx is year, month, day, hour, minute, or second, to a Date (replace xxxx with the year, month, etc)


///////////////////////////
// Constant Declarations //
///////////////////////////

// Array of days in each month
var daysInMonth = makeArray(12);
daysInMonth[0] = 31;
daysInMonth[1] = 29;   // must programmatically check this for leap year
daysInMonth[2] = 31;
daysInMonth[3] = 30;
daysInMonth[4] = 31;
daysInMonth[5] = 30;
daysInMonth[6] = 31;
daysInMonth[7] = 31;
daysInMonth[8] = 30;
daysInMonth[9] = 31;
daysInMonth[10] = 30;
daysInMonth[11] = 31;


////////////////////////////////////
// General Date Utility Functions //
////////////////////////////////////

// Days in February
// Given integer argument year, returns number of days in February of that year.
function daysInFebruary (year)
{
    // February has 29 days in any year evenly divisible by four,
    // EXCEPT for centennial years which are not also divisible by 400
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0) ) ) ? 29 : 28 );
}

// Days in Month
// Returns the days in the month of the given month and year
function getDaysInMonth(month, year)
{
    // Assume that if the month isn't a number, it represents the "blank" value
    if(isNaN(month))
    {
        return 0;
    }
    else if(month == 2)
    {
        return daysInFebruary(year);
    }
    else if(month >= 1 && month < daysInMonth.length)
    {
        return daysInMonth[month - 1];
    }

    // If it's invalid, just return 0
    return 0;
}

// Hour of Day
// Returns the hour in the day, from 0 to 23, that the given hour/AM combination yield
function hourOfDay(hh, am)
{
    if (hh == 12)
        hh = 0;

    return (am == 'AM' ? hh : Number(hh) + 12);
}

// Year from Date
// Returns the full year value from the Date
function yearFromDate(dateIn)
{
    return dateIn.getFullYear();
}

// Month from Date
// Returns the month value (plus 1, to normalize) from the Date
function monthFromDate(dateIn)
{
    return dateIn.getMonth() + 1;
}

// Day from Date
// Returns the day value from the Date
function dayFromDate(dateIn)
{
    return dateIn.getDate();
}

// Hour from Date
// Returns the 12-hour hour value from the Date
function hourFromDate(dateIn)
{
    return hourFromHourOfDay(hourOfDayFromDate(dateIn));
}

// Hour Of Day from Date
// Returns the 24-hour value from the Date
function hourOfDayFromDate(dateIn)
{
    return nvl(dateIn.getHours(), 0);
}

// Minute from Date
// Returns the minute value from the Date
function minuteFromDate(dateIn)
{
    return lpad(nvl(dateIn.getMinutes(), 0), 2, '0');
}

// Second from Date
// Returns the minute value from the Date
function secondFromDate(dateIn)
{
    return nvl(dateIn.getSeconds(), 0);
}

// AM from Date
// Returns the AM value from the Date
function amFromDate(dateIn)
{
    return amFromHourOfDay(hourOfDayFromDate(dateIn));
}

// Hour from Hour Of Day
// Returns the 12-hour hour value from the Hour of Day
function hourFromHourOfDay(hh24)
{
    var retVal = hh24 % 12;

    return (retVal == 0?12:retVal);
}

// AM from Hour Of Day
// Returns the AM value from the Hour of Day
function amFromHourOfDay(hh24)
{
    if(hh24 <= 11)
        return "AM";
    else
        return "PM";
}

///////////////////////////////
// Date Logic and Arithmetic //
///////////////////////////////

// Compare Dates
// Returns -1 if the first date is earlier, 1 if the second date is earlier, and 0 if the two dates are equal
function compareDates(date1, date2)
{
    if(date1 < date2)
        return -1;
    else if(date1 > date2)
        return 1;
    else
        return 0;
}

// Date Add
// Returns the startDate with the number of years, months, days, hours, minutes, and seconds added to it
// *U* - I've only checked with one month.  Check with negatives, and any number of the rest (as well as months that put it more than 1 year in the future)
function dateAdd(startDate, numYears, numMonths, numDays, numHours, numMinutes, numSeconds)
{
    var returnDate = new Date(startDate.getTime());
    var yearsToAdd = numYears;

    var month = returnDate.getMonth() + numMonths;
    while (month > 11)
    {
        yearsToAdd = Math.floor((month+1)/12);
        month -= 12*yearsToAdd;
        yearsToAdd += numYears;
    }
    returnDate.setMonth(month);
    returnDate.setFullYear(returnDate.getFullYear() + yearsToAdd);
    
    returnDate.setTime(returnDate.getTime()+1000*60*60*24*numDays+1000*60*60*numHours+1000*60*numMinutes+1000*numSeconds);
    
    return returnDate;

}

// Year Add
// Returns the startDate with the number of years added to it
function yearAdd(startDate, numYears)
{
    return dateAdd(startDate,numYears,0,0,0,0,0);
}

// Month Add
// Returns the startDate with the number of months added to it
function monthAdd(startDate, numMonths)
{
    return dateAdd(startDate,0,numMonths,0,0,0,0);
}

// Day Add
// Returns the startDate with the number of days added to it
function dayAdd(startDate, numDays)
{
    return dateAdd(startDate,0,0,numDays,0,0,0);
}

// Hour Add
// Returns the startDate with the number of hours added to it
function hourAdd(startDate, numHours)
{
    return dateAdd(startDate,0,0,0,numHours,0,0);
}

// Minute Add
// Returns the startDate with the number of minutes added to it
function minuteAdd(startDate, numMinutes)
{
    return dateAdd(startDate,0,0,0,0,numMinutes,0);
}

// Second Add
// Returns the startDate with the number of seconds added to it
function secondAdd(startDate, numSeconds)
{
    return dateAdd(startDate,0,0,0,0,0,numSeconds);
}
