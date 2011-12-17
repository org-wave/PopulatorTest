package org.wave.test.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.persistence.EntityManager;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wave.populator.core.Populator;
import org.wave.populator.enums.ErrorEnum;
import org.wave.populator.enums.FixedPatternEnum;
import org.wave.populator.exceptions.PopulatorException;
import org.wave.test.entities.EntidadeBasic;
import org.wave.test.entities.EntidadeComAtributo;
import org.wave.test.entities.EntidadeComAtributoEColecaoDeMesmoTipo;
import org.wave.test.entities.EntidadeComAtributosFixos;
import org.wave.test.entities.EntidadeManyToMany;
import org.wave.test.entities.EntidadeManyToManyInverse;
import org.wave.test.entities.EntidadeOneToMany;
import org.wave.test.entities.EntidadeOneToManyBidirecional;
import org.wave.test.entities.EntidadeOneToManyBidirecionalInverse;
import org.wave.test.entities.EntidadeOneToOne;
import org.wave.test.entities.EntidadeOneToOneBidirecional;
import org.wave.test.entities.EntidadeOneToOneBidirecionalInverse;
import org.wave.test.entities.W;
import org.wave.test.entities.X;
import org.wave.test.entities.Y;
import org.wave.test.entities.Z;

public class PopulatorTest {
	
	private Populator populator;

	private EntityManager manager;

	@Before
	public void setUp() {
		WeldContainer container = new Weld().initialize();
		this.populator = container.instance().select(Populator.class).get();

		this.manager = container.instance().select(EntityManager.class).get();
	}

	@Test(expected = PopulatorException.class)
	public void deveLancarExcecaoQuandoAClasseForNulaException() throws PopulatorException {
		this.populator.populate(null);
	}

	@Test
	public void deveLancarExcecaoQuandoAClasseForNula() {
		try {
			this.populator.populate(null);
		} catch (PopulatorException e) {
			assertEquals(ErrorEnum.NULL.getMessage(), e.getMessage());
		}
	}

	@Test
	public void devePersistirUmaEntidadeBasic() throws PopulatorException {
		EntidadeBasic instance = this.populator.populate(EntidadeBasic.class);

		assertNotNull(instance.getId());
		assertNotNull(instance.getVersion());
		assertTrue(instance.getActive());
	}

	@Test
	public void devePersistirUmaEntidadeOneToOne() throws PopulatorException {
		EntidadeOneToOne instance = this.populator.populate(EntidadeOneToOne.class);

		assertNotNull(instance.getId());
		assertNotNull(instance.getVersion());
		assertEquals(FixedPatternEnum.STRING.getValue(), instance.getStringField());

		assertNotNull(instance.getEntidadeBasic().getId());
		assertNotNull(instance.getEntidadeBasic().getVersion());
	}

	@Test
	public void devePersistirUmaEntidadeOneToMany() throws PopulatorException {
		EntidadeOneToMany instance = this.populator.populate(EntidadeOneToMany.class);

		assertNotNull(instance.getId());
		assertNotNull(instance.getVersion());
		assertEquals(FixedPatternEnum.STRING.getValue(), instance.getStringField());

		Collection<EntidadeOneToOne> itens = instance.getColecao();
		for (EntidadeOneToOne item : itens) {
			assertNotNull(item.getId());
			assertNotNull(item.getVersion());
		}
	}

	@Test
	public void devePersistirUmaEntidadeOneToOneBidirecional() throws PopulatorException {
		EntidadeOneToOneBidirecional instance = this.populator.populate(EntidadeOneToOneBidirecional.class);

		assertNotNull(instance.getId());
		assertNotNull(instance.getVersion());
		assertEquals(FixedPatternEnum.STRING.getValue(), instance.getStringField());

		EntidadeOneToOneBidirecionalInverse objetoOneToOneBidirecionalInverse = instance.getEntidadeOneToOneBidirecionalInverse();
		assertNotNull(objetoOneToOneBidirecionalInverse.getId());
		assertNotNull(objetoOneToOneBidirecionalInverse.getVersion());
		assertNotNull(objetoOneToOneBidirecionalInverse.getEntidadeOneToOneBidirecional());
	}

	@Test
	public void devePersistirUmaEntidadeOneToManyBidirecional() throws PopulatorException {
		EntidadeOneToManyBidirecional instance = this.populator.populate(EntidadeOneToManyBidirecional.class);

		assertNotNull(instance.getId());
		assertNotNull(instance.getVersion());
		assertEquals(FixedPatternEnum.STRING.getValue(), instance.getStringField());

		Collection<EntidadeOneToManyBidirecionalInverse> itens = instance.getColecao();
		for (EntidadeOneToManyBidirecionalInverse item : itens) {
			assertNotNull(item.getId());
			assertNotNull(item.getVersion());
			assertNotNull(item.getEntidadeOneToManyBidirecional());
		}
	}

	@Test
	public void devePersistirUmaEntidadeManyToMany() throws PopulatorException {
		EntidadeManyToMany instance = this.populator.populate(EntidadeManyToMany.class);

		assertNotNull(instance.getId());
		assertNotNull(instance.getVersion());
		assertEquals(FixedPatternEnum.STRING.getValue(), instance.getStringField());

		Collection<EntidadeManyToManyInverse> itens = instance.getColecao();
		for (EntidadeManyToManyInverse item : itens) {
			assertNotNull(item.getId());
			assertNotNull(item.getVersion());

			Collection<EntidadeManyToMany> elementos = item.getColecao();
			for (EntidadeManyToMany elemento : elementos) {
				assertEquals(instance, elemento);
			}
		}
	}

	@Test
	public void devePersistirEntidades() throws PopulatorException {
		EntidadeOneToOne instance01 = this.populator.populate(EntidadeOneToOne.class);
		assertNotNull(instance01.getId());
		assertNotNull(instance01.getVersion());
		assertTrue(instance01.getActive());

		EntidadeOneToOne instance02 = this.populator.populate(EntidadeOneToOne.class);
		assertNotNull(instance02.getId());
		assertNotNull(instance02.getVersion());
		assertTrue(instance02.getActive());

		assertTrue(this.manager.contains(instance01));
		assertNotNull(this.manager.find(EntidadeOneToOne.class, instance01.getId()));

		assertTrue(this.manager.contains(instance02));
		assertNotNull(this.manager.find(EntidadeOneToOne.class, instance02.getId()));

		assertFalse(instance01.equals(instance02));
	}

	@Test
	public void deveRemoverUmaEntidadePersistida() throws PopulatorException {
		EntidadeBasic instance = this.populator.populate(EntidadeBasic.class);

		assertNotNull(instance.getId());
		assertNotNull(instance.getVersion());
		assertTrue(instance.getActive());

		this.populator.clear();

		assertFalse(this.manager.contains(instance));
		assertNull(this.manager.find(EntidadeBasic.class, instance.getId()));
	}

	@Test
	public void deveRemoverEntidadesPersistidas() throws PopulatorException {
		EntidadeOneToOne instance01 = this.populator.populate(EntidadeOneToOne.class);
		assertNotNull(instance01.getId());
		assertNotNull(instance01.getVersion());
		assertTrue(instance01.getActive());

		EntidadeOneToOne instance02 = this.populator.populate(EntidadeOneToOne.class);
		assertNotNull(instance02.getId());
		assertNotNull(instance02.getVersion());
		assertTrue(instance02.getActive());

		this.populator.clear();

		assertFalse(this.manager.contains(instance01));
		assertNull(this.manager.find(EntidadeBasic.class, instance01.getId()));

		assertFalse(this.manager.contains(instance02));
		assertNull(this.manager.find(EntidadeBasic.class, instance02.getId()));
	}

	@Test
	public void devePersistirUmaInstanciaQuandoHouverUmObjetoCompartilhadoPorOutros() throws PopulatorException {
		X instance = this.populator.populate(X.class);

		assertNotNull(instance.getId());

		Y y = instance.getY();
		assertNotNull(y.getId());

		Z z = instance.getZ();
		assertNotNull(z.getId());

		W w = y.getW();
		assertNotNull(w.getId());

		assertEquals(w, z.getW());
	}

	@Test
	public void devePersistirUmaEntidadeComAtributoEColecaoDeMesmoTipo() throws PopulatorException {
		EntidadeComAtributoEColecaoDeMesmoTipo instance = this.populator.populate(EntidadeComAtributoEColecaoDeMesmoTipo.class);
		assertNotNull(instance.getId());
		assertNotNull(instance.getVersion());
		assertTrue(instance.getActive());

		EntidadeComAtributo entidadeComAtributo = instance.getEntidadeComAtributo();
		assertNotNull(entidadeComAtributo);
		assertNotNull(entidadeComAtributo.getId());
		assertNotNull(entidadeComAtributo.getVersion());
		assertTrue(entidadeComAtributo.getActive());

		EntidadeComAtributosFixos entidadeComAtributosFixos = entidadeComAtributo.getEntidadeComAtributosFixos();
		assertNotNull(entidadeComAtributosFixos);
		assertNotNull(entidadeComAtributosFixos.getId());
		assertNotNull(entidadeComAtributosFixos.getVersion());
		assertTrue(entidadeComAtributosFixos.getActive());

		Collection<EntidadeComAtributo> elementos = instance.getColecao();
		for (EntidadeComAtributo elemento : elementos) {
			assertEquals(elemento, entidadeComAtributo);

			assertEquals(entidadeComAtributosFixos, elemento.getEntidadeComAtributosFixos());
		}
	}

	@After
	public void tearDown() throws PopulatorException {
		this.populator.clear();
	}

}
