# IM Cyclic

## Cyclic Graph

Call Hierarchy uses TreeNode to build the graph even though tree is acyclic. The graph is not built before hand but is built as and when IM is visited by main visitor. First IM is tested whether it is cycic, if so then it not added to tree without processing IM. Otherwise it is added to call hierarchy tree and process goes through.

## Depth First Iterator

The depth first iterator order is as follows

    Tree

    1
    ├── 2
    │   ├── 3
    │   │   ├── 4
    │   │   └── 5
    │   └── 6
    ├── 7
    └── 8
        ├── 9
        │   ├── 10
        │   └── 11
        └── 12

    Depth First iterator order

    1  2  3  4  5  6  7  8  9  10  11  12

The children field in TreeNode is a List so older nodes lands in left and newer in right. From root 1 the brach 2 is first traversed as 1,2,3,4. Next backtracked to 3 and its second branch is traversed as 5. Then again backtracked to 2 and its branch 6 is visited. After that backtracked to 1 and its second branch 7 is visited and then back tracked again to 1 and its last branch 8 is explored in same fashion.

## Cyclic

For a invoker, invoked pair, get leaves nodes whose object is invoker. If not empty for last node get path to root and check whether invoked exits in the path and if exists then it is cyclic. If leaves list is empty then get other nodes whose object is invoker and check whether cyclic as explained before. In both cases if the list contains multiple nodes then the last one choosen as it is the latest in depth first traverse.

If cyclic then IM processMethod returns without processing the method otherwise the invoked is added to the call hierarchy graph. For a invoker, invoked pair, get leaves nodes whose object is invoker. If not empty for last node add the invoked provided the invoked doesn't exists in the last node children list. For example, if im1 calls im2 twice then only one im2 is added to im1 since both im2 calls are same. If leaves list is empty then get other nodes whose object is invoker and add the node as explained before. In both cases if the list contains multiple nodes then the last one choosen as it is the latest in depth first traverse.

## Examples

Node add and cyclic logic is as below.

    1
       2
          3
             4
                1
       3
          4
             1

    12341
    1,2     trv leaf 1, no 2, not cyclic
            find leaf 1, add 2
    2,3     find leaf 2, trv 12, no 3, not cyclic
            find leaf 2, add 3
    3,4     find leaf 3, trv 123, no 4, not cyclic
            find leaf 3, add 4
    4,1     find leaf 4, trv 1234, found 1, cyclic
            no add

    1341
    1,3     find leaf 1, not found, find 1, no child 3, trv 1, no 3, not cyclic
            find leaf 1, not found, find 1, no child 3, add 3
    3,4     find leaf 3, trv 13, no 4, not cyclic
            find leaf 3, add 4
    4,1     find leaf 4, trv 134, found 1, cyclic
            no add

-----------

    1
       2
          3

       3

    123
    1,2     trv leaf 1, no 2, not cyclic
            find leaf 1, add 2
    2,3     find leaf 2, trv 12, no 3, not cyclic
            find leaf 2, add 3

    13
    1,3     find leaf 1, not found, find 1, no child 3, trv 1, no 3, not cyclic
            find leaf 1, not found, find 1, no child 3, add 3

-----------

    1
      2
        3
          1

    1231
    1,2     trv leaf 1, no 2, not cyclic
            find leaf 1, add 2
    2,3     find leaf 2, trv 12, no 3, not cyclic
            find leaf 2, add 3
    3,1     find leaf 3, trv 123, found 1, cyclic
            no add

-----------

    1
      2
        3
          1

      3
        1

    1,2     trv leaf 1, no 2, not cyclic
            find leaf 1, add 2
    2,3     find leaf 2, trv 12, no 3, not cyclic
            find leaf 2, add 3
    3,1     find leaf 3, trv 123, found 1, cyclic
            no add

    131
    1,3     find leaf 1, not found, find 1, no child 3, trv 1, no 3, not cyclic
            find leaf 1, not found, find 1, no child 3, add 3
    3,1     find leaf 3, trv 13, found 1, cyclic
            no add

-----------

    1
      2
        3
          4
            3
      4
        3

    12343
    1,2     trv leaf 1, no 2, not cyclic
            find leaf 1, add 2
    2,3     find leaf 2, trv 12, no 3, not cyclic
            find leaf 2, add 3
    3,4     find leaf 3, trv 123, no 4, not cyclic
            find leaf 3, add 4
    4,3     find leaf 4, trv 1234, found 3, cyclic
            no add

    143
    1,4     find leaf 1, not found, find 1, no child 4, trv 1, no 4, not cyclic
            find leaf 1, not found, find 1, no child 4, add 4
    4,3     find leaf 4, trav 1234, found 3 cyclic
            no add