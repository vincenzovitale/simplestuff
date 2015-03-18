# Introduction #

Here a list of future improvements to be implemented.


# Improvements #


1 MATCHING SUBCLASSES:

> I was thinking about how to compare the class in a more flexible manner on my way here, and come up with the following suggestion:

> Instead of

> boolean includeClass...son default true

> how about

> Class[.md](.md) comparableClasses default null
> boolean matchSubclasses default false

> This way, the user can **specify** at the class level

> a) whether class information should be included in the comparison (and hash code calculation, we shouldn't forget that)
> b) **which** other classes are acceptable matches
> c) whether the class to be compared must be one of the given classes, or may be a subclass

> I think this would cover all the cases we've dicussed, and be Hibernate-safe as well. The logic would be

> if (comaprableClasses != null) {

> if (!matchSubclasses) {

> // otherBeanClass must be one of the comparableClasses

> } else {

> // one of the comparable classes must be a superclass of otherBeanClass

> }

> }