import com.discoperi.model.KunderaEntityManageFactory;
import com.discoperi.model.mongo.dao.ImageDao;
import com.discoperi.model.mongo.dao.impl.ImageDaoImpl;
import com.discoperi.model.service.ImageComputationService;
import com.discoperi.model.service.ImageService;
import com.discoperi.model.service.ImageSourceService;
import com.discoperi.model.service.impl.ImageComputationImpl;
import com.discoperi.model.service.impl.ImageServiceImpl;
import com.discoperi.model.service.impl.ImageSourceImpl;
import com.google.inject.AbstractModule;

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
 * <p>
 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
public class Module extends AbstractModule {

	@Override
	public void configure( ) {
		bind( ImageService.class ).to( ImageServiceImpl.class );
		bind( ImageDao.class ).to( ImageDaoImpl.class );
		bind( ImageComputationService.class ).to( ImageComputationImpl.class );
		bind( ImageSourceService.class).to( ImageSourceImpl.class );
		bind( KunderaEntityManageFactory.class ).asEagerSingleton( );
	}

}
