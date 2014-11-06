package ca.ulaval.glo4003.appemployee.services;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.repository.DepartmentRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;


public class DepartmentServiceTest {
    
    private UserRepository userRepositoryMock;
    private DepartmentRepository departementRepository;
    
    @Before
    public void init() {
        userRepositoryMock = mock(UserRepository.class);
        departementRepository = mock(DepartmentRepository.class);
    }
    
    @Test
    public void getCurrentPayPeriodReturnsPayPeriodIfSuccessful() {

    }

}
