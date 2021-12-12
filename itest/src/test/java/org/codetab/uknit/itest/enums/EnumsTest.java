package org.codetab.uknit.itest.enums;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.enums.Account.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class EnumsTest {
    @InjectMocks
    private Enums enums;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testQualifiedName() {
        Account a = Mockito.mock(Account.class);
        Type apple = Type.SAVINGS;

        Type actual = enums.qualifiedName(a);

        assertSame(apple, actual);
    }

    @Test
    public void testAssignment() {
        Account a = Mockito.mock(Account.class);
        Type t = Type.OVERDRAFT;

        Type actual = enums.assignment(a);

        assertSame(t, actual);
    }

    @Test
    public void testMi() {
        Account a = Mockito.mock(Account.class);
        Type apple = Account.Type.OVERDRAFT;

        when(a.getType()).thenReturn(apple);

        Type actual = enums.mi(a);

        assertSame(apple, actual);
    }

    @Test
    public void testAssignmentMi() {
        Account a = Mockito.mock(Account.class);
        Type t = Account.Type.OVERDRAFT;

        when(a.getType()).thenReturn(t);

        Type actual = enums.assignmentMi(a);

        assertSame(t, actual);
    }

    @Test
    public void testParameter() {
        Type type = Account.Type.OVERDRAFT;

        Type actual = enums.parameter(type);

        assertSame(type, actual);
    }
}
