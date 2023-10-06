# TODO

## Optional

Optional is empty initialize with value.

    public boolean collect(final Optional<List<Date>> dates, final Date inDate) {
        return dates.get().get(0).equals(inDate);
    }
   
    Optional<List<Date>> dates = Optional.empty();
    Date inDate = Mockito.mock(Date.class);
    Date date = Mockito.mock(Date.class);

also See: itest.optional.Lists and Ops.
