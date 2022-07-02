package org.codetab.uknit.itest.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.model.Account;
import org.codetab.uknit.itest.model.Account.Type;
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
        Type type = Type.SAVINGS;

        Type actual = enums.qualifiedName(a);

        assertEquals(type, actual);
    }

    @Test
    public void testAssignment() {
        Account a = Mockito.mock(Account.class);
        Type t = Type.OVERDRAFT;

        Type actual = enums.assignment(a);

        assertEquals(t, actual);
    }

    @Test
    public void testMi() {
        Account a = Mockito.mock(Account.class);
        Type type = Account.Type.OVERDRAFT;

        when(a.getType()).thenReturn(type);

        Type actual = enums.mi(a);

        assertEquals(type, actual);
    }

    @Test
    public void testAssignmentMi() {
        Account a = Mockito.mock(Account.class);
        Type t = Account.Type.OVERDRAFT;

        when(a.getType()).thenReturn(t);

        Type actual = enums.assignmentMi(a);

        assertEquals(t, actual);
    }

    @Test
    public void testParameter() {
        Type type = Account.Type.OVERDRAFT;

        Type actual = enums.parameter(type);

        assertEquals(type, actual);
    }
}
