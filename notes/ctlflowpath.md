

## Enable and Disable Logic

By default all treeNodes are enabled. On creation optional items such as else and catch are disabled. Only one instance of each of such items is enabled later with enableUncoveredNodes() method.


## Else vs else if

In if else the else block is added as branch.

    IfStatement 6683+
    ├── Block if 9817+
    └── Block else 9277+
        
There is only one visit and we add ifStmt 6683, if block 9817 and else block 9277.        

In else if the if statment of else is added as branch.

    IfStatement 9817+
    ├── Block if 9277+
    └── IfStatement else 1948+
        ├── Block if 5023+
        └── Block empty else 9263+

In first ifStmt visit we add ifstmt 9817, if block 9277 and ifStmt of else 1948. In next visit we find the ifStmt of else 1948 (no need to add if since it is already part of the tree) and add if block 5023 and empty else 9263.

## Sequential Ctl Flow Nodes and Path Order

When two ctl flow blocks are one after another then for all paths of first block the main path of second block are added.

### Example 1

    if      1
    else    2

    if      3    
    else    4

Then paths are 1-3, 1-4 and 2-3   

    └── IfStatement 1+
        ├── Block if 1+
        │   └── IfStatement 3+                  
        │       ├── Block if 3+                 1-3
        │       └── Block else 4+               1-4
        └── Block else 2+
            └── IfStatement 3+
                ├── Block if 3+                 2-3
                └── Block else 4
    
### Example 2

    if       1
    else if  2
    else     3

    try      4    
    catch    5

Then paths are 1-4, 1-5, 2-4 and 3-4

    └── IfStatement 
        ├── Block if 1+
        │   └── TryStatement 
        │       ├── Block try 4+
        │       └── Block try 4+
        │           └── CatchClause 5+          1-4
        │               └── Block catch 5+      1-5
        └── IfStatement else 2+
            ├── Block if 2+
            │   └── TryStatement 4+         
            │       └── Block try 4+            2-4
            └── Block else 3+
                └── TryStatement 4+
                    └── Block try 4+            3-4

### Example 3

    9753    if (canSwim) {                  1      
    8219        duck.swim("if");
    5956    } else if (canDive) {           2
    0630        duck.swim("else if");       
            }
    5736    empty else                      3

    2661    if (canFlip) {                  4
    7337        duck.swim("plus if");
    2153    } else if (canFly) {            5
    2016        duck.swim("plus else if");
            }
    5411    empty else                      6
    
    
Effective paths are: 1-4, 1-5, 1-6 (main paths) and 2-4, 3-4 (secondary paths)
    
Main Path: The first if's then (9753-8219) is the main path. To it, all paths of second if are added. This results in 3 tests - canSwim-canFlip, canSwim-canFly and canSwim-2nd emptyElse. 

Secondry Paths: The first if's else-if is the head of secondary path. It branches to two test canDive and 1st emptyElse. To complete the paths of these branches, second if's then (canFlip) is added to both. This results in two tests - canDive-canFlip and 1st emptyElse-canFlip.

    MethodDeclaration 7490+
    └── Block md 8980+
        └── IfStatement 9753+                           if (canSwim)

            ======== Main Path ========
            
	        ├── Block if 8219+                              duck.swim("if)
	        │   └── IfStatement 2661+                   if (canFlip)
	        │       ├── Block if 7337+                      duck.swim("plus if");
	        │       └── IfStatement else 2153+          else if (canFly)
	        │           ├── Block if 2016+                  duck.swim("plus else if")
	        │           └── Block empty else 5411+      empty else 

	        ====== Secondary Path ======
	        
	        └── IfStatement else 5956+                  else if (canDive)
	            ├── Block if 0630+                      
	            │   └── IfStatement 2661+               if (canFlip)
	            │       ├── Block if 7337+                  duck.swim("plus if")
	            │       └── IfStatement else 2153       
	            └── Block empty else 5736+              empty else
	                └── IfStatement 2661+               if (canFlip)
	                    ├── Block if 7337+                  duck.swim("plus if")
	                    └── IfStatement else 2153       

## Finally block

Finally blocks, if exists, are added to try and to catch blocks. When ctl flow block's parent is TryStmt or CatchClause then we look for the finally with findTerminalNode() and add the next block to it.




    
