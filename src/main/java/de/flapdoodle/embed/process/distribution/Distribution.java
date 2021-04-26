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
package de.flapdoodle.embed.process.distribution;

import org.immutables.value.Value;
import org.immutables.value.Value.Parameter;

/**
 *
 */
@Value.Immutable
public abstract class Distribution {

	@Parameter
	public abstract Version version();

	@Parameter
	public abstract Platform platform();

	@Parameter
	public abstract BitSize bitsize();

	@Parameter
	public abstract Architecture architecture();

	@Override
	public String toString() {
		return "" + version() + ":" + platform() + ":" + architecture() + ":" + bitsize();
	}

	public static Distribution detectFor(Version version) {
		return of(version, Platform.detect(), BitSize.detect(), Architecture.detect());
	}

	public static Distribution of(Version version, Platform platform, BitSize bitsize) {
		return of(version, platform, bitsize, Architecture.detect());
	}

	public static Distribution of(Version version, Platform platform, BitSize bitsize, Architecture arch) {
		return ImmutableDistribution.of(version, platform, bitsize, arch);
	}
}
