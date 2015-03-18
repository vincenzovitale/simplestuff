## SimpleBean ##

This utility is intended to prevent developers having to write (and maintain) code usually written when working with classical POJO beans. It uses annotation to explicitly declare a ''@BusinessField'' in a ''@BusinessObject'' annotated class.

It was initially created to easily implement the ''equals(Object obj)'', ''hashCode()'' and ''toString()'' methods; i.e. when using Hibernate these methods can be defined if the persistence framework needs to be aware of the business relevant fields to perform better comparisons. In this scenario, when a "business field" is added, maintenance of ''equals'' and ''hashCode'' is often error-prone. With Hibernate, for example, you may end up working not with bean objects but CGLIB proxies, and in this case you '''must''' use getters when doing comparisons to avoid obtaining null values instead of the intended data.

With SimpleBean you write:

> @BusinessObject
> public static class ParentClass{
> > @BusinessField
> > String stringField;


> @BusinessField
> String[.md](.md) stringArrayField;

> @BusinessField
> Boolean booleanField;

> @BusinessField
> boolean booleanPrimitiveField;

> @BusinessField
> Date dateField;

> @BusinessField
> Set

&lt;ChildClass&gt;

 childs;

> @Override
> public boolean equals(Object obj) {
> > return BeanUtils.equals(this, obj);

> }

> @Override
> public int hashCode() {
> > return BeanUtils.hashCode(this);

> }

> @Override
> public String toString() {
> > return BeanUtils.toString(this);

> }

> ...<Getters and Setters>...
> }

Only the annotated fields will be considered. It's also possible to specify, for each field, where to consider it. For example:

> @BusinessField(includeInEquals=true,includeInHashCode=true,includeInToString=false)
> private String myField;

means that ''myField'' will be used in ''equals'' and ''hashCode'' replacement but not in ''toString''. The default value for each property is ''true''.

Another method included in BeanUtils is

> public static 

&lt;T&gt;

 T getTestBean(Class

&lt;T&gt;

 beanClass, String suffix);

which returns a test object with all the ''@BusinessField'' annotated fields set to their default values. The ''suffix'' parameter is added where possible (i.e. to all String fields), which allows non-equal test beans to be created. This can be essential, for instance, if the beans are to be persisted to a table with unique contraints on relevant columns.

For example:

> ParentClass testBean = BeanUtils.getTestBean(ParentClass.class, "test");