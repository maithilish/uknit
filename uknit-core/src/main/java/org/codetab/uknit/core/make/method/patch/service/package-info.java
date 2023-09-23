/**
 * Services to patch different ASTNodes. Keep following things in mind while
 * coding the service.
 *
 * Remove the cast and parenthesized exp with Wrappers.unpack() method. In
 * getExps() method use Wrappers.strip() which removes only the Parenthesise
 * from the exp.
 *
 * Don't unpack the list of exps such as MI.arguments() etc., as we need the
 * list returned by the copy to patch. The unpack on list creates a new list
 * from the original list and any patch applied on new list will not reflect in
 * the node copy. For such lists, unpack each exp individually in the for loop.
 *
 */
package org.codetab.uknit.core.make.method.patch.service;
