package org.codetab.uknit.itest.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.enums.Model.Account;
import org.codetab.uknit.itest.enums.Model.Account.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class UseEnumTest {
    @InjectMocks
    private UseEnum useEnum;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testQualifiedName() {
        Account a = Mockito.mock(Account.class);
        Type type = Type.SAVINGS;

        Type actual = useEnum.qualifiedName(a);

        assertEquals(type, actual);
    }

    @Test
    public void testAssignment() {
        Account a = Mockito.mock(Account.class);
        Type t = Type.OVERDRAFT;

        Type actual = useEnum.assignment(a);

        assertEquals(t, actual);
    }

    @Test
    public void testMi() {
        Account a = Mockito.mock(Account.class);
        Type type = Type.OVERDRAFT;

        when(a.getType()).thenReturn(type);

        Type actual = useEnum.mi(a);

        assertEquals(type, actual);
    }

    @Test
    public void testAssignmentMi() {
        Account a = Mockito.mock(Account.class);
        Type t = Type.OVERDRAFT;

        when(a.getType()).thenReturn(t);

        Type actual = useEnum.assignmentMi(a);

        assertEquals(t, actual);
    }

    @Test
    public void testParameter() {
        Type type = Type.OVERDRAFT;

        Type actual = useEnum.parameter(type);

        assertEquals(type, actual);
    }
}
