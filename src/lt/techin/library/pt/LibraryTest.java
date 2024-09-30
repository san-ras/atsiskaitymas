package lt.techin.library.pt;

import lt.techin.library.pt.test.AbstractLibraryTest;

public class LibraryTest extends AbstractLibraryTest {
    @Override
    protected BookCatalog createBookCatalog() {
        return new BookCatalogSandraR();
    }
}
