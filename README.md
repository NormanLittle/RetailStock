# RetailStock
Technical test to implement stock level check for products sold by a retailer. 

# Problem Description
Implement a system to check the stock levels for the products sold by a retailer and apply a number of ‘rules’ before returning advice for which products should be ordered.

# Acceptance Criteria
1.  The input and output can be via any medium. 
2.	The rules to be applied can be stored however you think is most appropriate.
3.	*Optional*: In addition to returning the output of the stock-check, the results should be persisted to a database to maintain an audit of the stock-check and recommended purchase history.

# Rules
* There should be a rule allowing specification of a minimum stock level for a product.
* There should be a rule allowing products to be marked as blocked (and, as such, can not be ordered). 
* It should be possible to add a rule to order an additional volume of a certain product. 

# Example
5 products and their stock levels:
```
a,5
b,8
c,2
d,0
e,1
```
*	Products a,b,c & e have a minimum stock level of 4.
*	Product d has a minimum stock level of 8.
*	Product c is blocked and new stock should not be ordered.
*	Product d has a one-off order of 15.
