package com.kakao.rule.engine;

import com.kakao.event.Event;
import com.kakao.event.NewAccountEvent;
import com.kakao.rule.book.FishingCatchRule;
import com.kakao.rule.scheme.AccountHandler;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertFalse;

public class RuleBookBuilderTest {
  private AccountHandler accountHandler = new AccountHandler();

  @Mock
  private Event event;

  @Test
  public void 신규계좌만_만들고_피싱룰을_적용하면_false_가_발생한다() throws Exception {
    MockitoAnnotations.initMocks(this);

    when(event.getType()).thenReturn(NewAccountEvent.class.getSimpleName());
    when(event.getCustomerNumber()).thenReturn((long) 1);
    when(event.getAmount()).thenReturn((long) 5000);
    when(event.getTimestamp()).thenReturn(System.currentTimeMillis());
    when(event.getAccountNumber()).thenReturn((long) 1234);

    accountHandler.apply(event);

    FishingCatchRule ruleBook = new FishingCatchRule(accountHandler, event);
    assertFalse(ruleBook.define().run());
  }
}

