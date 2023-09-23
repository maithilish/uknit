/**
 * In many cases the expression in a node needs to be replaced by a var name. To
 * generate a test class uKnit may process a Method Declaration multiple times -
 * methods (private, super method) may be called one or more times internally,
 * and also the ctl flow path may result in multiple test methods. Direct
 * modification to node makes the node useless for subsequent processing. To
 * avoid modification to the original node, uKnit stages Patch which is simply
 * maps exp to a name in a node. Whenever a patched view of a node is required
 * or in test generation phase, a copy of original node is created and patch is
 * applied to the copy.
 *
 */
package org.codetab.uknit.core.make.method.patch;
