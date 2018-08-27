package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class ContentBlock extends AbstractSection {
    private final List<ContentUnit> block;

    public ContentBlock(List<ContentUnit> block) {
        Objects.requireNonNull(block, "block must not be null");
        this.block = block;
    }

    public List<ContentUnit> getBlock() {
        return block;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContentBlock that = (ContentBlock) o;
        return Objects.equals(block, that.block);
    }

    @Override
    public int hashCode() {
        return Objects.hash(block);
    }

    @Override
    public String toString() {
        return "ContentBlock{" +
                "block=" + block +
                '}';
    }
}
