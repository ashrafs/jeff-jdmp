/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.jdmp.core.script.jdmp.node;

import org.jdmp.core.script.jdmp.analysis.*;

@SuppressWarnings("nls")
public final class ALevel6Level7 extends PLevel7
{
    private PLevel6 _level6_;

    public ALevel6Level7()
    {
        // Constructor
    }

    public ALevel6Level7(
        @SuppressWarnings("hiding") PLevel6 _level6_)
    {
        // Constructor
        setLevel6(_level6_);

    }

    @Override
    public Object clone()
    {
        return new ALevel6Level7(
            cloneNode(this._level6_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseALevel6Level7(this);
    }

    public PLevel6 getLevel6()
    {
        return this._level6_;
    }

    public void setLevel6(PLevel6 node)
    {
        if(this._level6_ != null)
        {
            this._level6_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._level6_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._level6_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._level6_ == child)
        {
            this._level6_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._level6_ == oldChild)
        {
            setLevel6((PLevel6) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
