/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Martin Jöhren <m.joehren@googlemail.com>
 *
 * with contributions from
 * 	konstantin-ba@github,
	Archimedes Trajano (trajano@github),
	Kevin D. Keck (kdkeck@github),
	Ben McCann (benmccann@github)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flapdoodle.embed.process.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.junit.Test;

import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.Version;
import de.flapdoodle.embed.process.extract.ExtractedFileSet;
import de.flapdoodle.embed.process.io.directories.PropertyOrPlatformTempDir;
import de.flapdoodle.embed.process.io.file.Files;


public class StaticArtifactStoreTest {

	@Test
	public void returnStaticFileSetForDistribution() throws IOException {
		Distribution distribution=Distribution.detectFor(Version.of("13.7.121"));
		
		File generatedBaseDir=Files.createTempDir(PropertyOrPlatformTempDir.defaultInstance(),	"static");
		
		ExtractedFileSet fileSet=ExtractedFileSet.builder(generatedBaseDir)
				.baseDirIsGenerated(true)
				.executable(new File("bla.exe"))
				.addLibraryFiles(new File("foo.lib"))
				.build();
		
		IArtifactStore store = StaticArtifactStore.builder()
			.putFileSet(distribution, fileSet)
			.build();
		
		Optional<ExtractedFileSet> optExtractFileSet = store.extractFileSet(distribution);
		assertTrue(optExtractFileSet.isPresent());
		ExtractedFileSet extractFileSet = optExtractFileSet.get();
		
		assertEquals("bla.exe", extractFileSet.executable().getName());
		
		Files.forceDelete(generatedBaseDir);
	}
}
