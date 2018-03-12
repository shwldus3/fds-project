package com.kakao.rule.scheme;

import com.kakao.event.DepositEvent;
import com.kakao.event.Event;
import com.kakao.event.NewAccountEvent;
import com.kakao.event.WithdrawEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class AccountHandlerTest {
  private AccountHandler accountHandler = new AccountHandler();

  @Mock
  private Event event;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    when(event.getType()).thenReturn(NewAccountEvent.class.getSimpleName());
    when(event.getCustomerNumber()).thenReturn((long) 1);
    when(event.getAmount()).thenReturn((long) 5000);
    when(event.getTimestamp()).thenReturn(System.currentTimeMillis());
    when(event.getAccountNumber()).thenReturn((long) 1234);

    accountHandler.apply(event);
  }

  @Test
  public void 기존계좌에_저금하면_예상한_금액과_잔액이_동일합니다() throws Exception {
    when(event.getType()).thenReturn(DepositEvent.class.getSimpleName());
    when(event.getCustomerNumber()).thenReturn((long) 1);
    when(event.getAmount()).thenReturn((long) 10000);
    when(event.getTimestamp()).thenReturn(System.currentTimeMillis());
    when(event.getAccountNumber()).thenReturn((long) 1234);

    accountHandler.apply(event);

    assertEquals(10000, accountHandler.getDepositHistory().getLast(1234).getAmount());
    assertEquals(15000, accountHandler.getAcounts().getAccount(1).getBalance());
  }

  @Test
  public void 기존계좌에서_출금하면_예상한_금액과_잔액이_동일합니다() throws Exception {
    when(event.getType()).thenReturn(WithdrawEvent.class.getSimpleName());
    when(event.getCustomerNumber()).thenReturn((long) 1);
    when(event.getAmount()).thenReturn((long) 5000);
    when(event.getTimestamp()).thenReturn(System.currentTimeMillis());
    when(event.getAccountNumber()).thenReturn((long) 1234);

    accountHandler.apply(event);

    assertEquals(5000, accountHandler.getWithdrawHistory().getLast(1234).getAmount());
    assertEquals(0, accountHandler.getAcounts().getAccount(1).getBalance());
  }
}
